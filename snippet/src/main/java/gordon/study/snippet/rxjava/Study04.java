/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/bb58571cdb64
 *
 * Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件。
 * 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
 *
 * 组合的过程是分别从两根水管里各取出一个事件来进行组合, 并且一个事件只能被使用一次, 组合的顺序是严格按照事件发送的顺利来进行的。
 * 最终下游收到的事件数量是和上游中发送事件最少的那一根水管的事件数量相同。
 */
package gordon.study.snippet.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Study04 {

    public static void main(String[] args) {

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    System.out.println("sleep interrupted");
                }
                emitter.onNext(4);
            }
        })
                .doOnNext(val -> System.out.println("Observable1 onNext: " + val + " in thread " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                // 如果不调用onComplete，observable1会发射event 4，但是zip无法合并它。
                // 调用onComplete后，因为observable1发射event 4前在sleep，当zip捕获到observable2已完成后，会中断observable1，
                // 因此sleep会被InterruptedException中断。
                emitter.onComplete();
            }
        })
                .doOnNext(val -> System.out.println("Observable2 onNext: " + val + " in thread " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, (a, b) -> a + b)
                .doOnNext(val -> System.out.println("onNext: " + val + " in thread " + Thread.currentThread().getName()))
                .subscribe();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}
