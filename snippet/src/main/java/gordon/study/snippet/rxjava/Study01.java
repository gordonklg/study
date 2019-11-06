/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/464fa025229e
 * https://www.jianshu.com/u/c50b715ccaeb（系列）
 *
 * RxJava基于观察者模式：
 * Observable
 * Observer
 * Subscribe
 * Event
 */
package gordon.study.snippet.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Study01 {

    public static void main(String[] args) {

        // 创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            /*
             Emitter是发射器的意思，用来发出事件。它可以发出三种类型的事件，通过调用emitter的onNext(T value)、
             onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件。

             上游可以发送无限个onNext，下游也可以接收无限个onNext
             当上游发送了一个onComplete后，上游onComplete之后的事件将会继续发送，而下游收到onComplete事件之后将不再继续接收事件
             当上游发送了一个onError后，上游onError之后的事件将继续发送，而下游收到onError事件之后将不再继续接收事件
             上游可以不发送onComplete或onError
             最为关键的是onComplete和onError必须唯一并且互斥，即不能发多个onComplete，也不能发多个onError，也不能先发一个onComplete，然后再发一个onError，反之亦然
             */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("emit 1");
                emitter.onNext(1);
                System.out.println("emit 2");
                emitter.onNext(2);
                System.out.println("emit 3");
                emitter.onNext(3);
                System.out.println("emit complete event");
                emitter.onComplete();
                System.out.println("emit 4");
                emitter.onNext(4); // 不会被下游接受
                emitter.onComplete(); // 并不会出错！
                // emitter.onError(new RuntimeException()); // 系统异常！！
            }
        });

        // 创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            /*
            Disposable，字面意思是一次性用品。我们可以把它理解成两根管道之间的一个机关，当调用它的dispose()方法时，
            它就会将两根管道切断，从而导致下游收不到事件。

            注意: 调用dispose()并不会导致上游不再继续发送事件，上游会继续发送剩余的事件.
             */
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                this.disposable = d;
                System.out.println("Observer onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer onNext:" + value);
                if (value == 2) {
                    disposable.dispose();
                    System.out.println("Observer disposed");
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer onError");
            }

            @Override
            public void onComplete() {
                System.out.println("Observer onComplete");
            }
        };

        // 建立连接。当上游和下游建立连接之后，上游才会开始发送事件
        observable.subscribe(observer);


        ////////// 链式调用
        System.out.println();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(11);
                emitter.onNext(12);
                emitter.onNext(13);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        ////////// subscribe其它的重载方法
        System.out.println();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(21);
                emitter.onNext(22);
                emitter.onNext(23);
                emitter.onComplete();
            }
        }).subscribe(
                val -> System.out.println("onNext:" + val),
                e -> System.out.println("onError"),
                () -> System.out.println("onComplete"),
                d -> System.out.println("onSubscribe")
        );

    }
}
