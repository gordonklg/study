/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/8818b98c44e2
 *
 * 正常情况下，上游和下游是工作在同一个线程中的，也就是说上游在哪个线程发事件，下游就在哪个线程接收事件
 * 当我们在主线程中去创建一个上游Observable来发送事件，则这个上游默认就在主线程发送事件
 * 当我们在主线程去创建一个下游Observer来接收事件，则这个下游默认就在主线程中接收事件
 *
 * 多次调用 subscribeOn 和 observeOn 本文描述不是很清晰，再参考 https://www.jianshu.com/p/3e5d53e891db
 * ObserveOn: specify the Scheduler on which an observer will observe this Observable
 * 指定一个观察者在哪个调度器上观察这个Observable
 * SubscribeOn: specify the Scheduler on which an Observable will operate
 * 指定Observable自身在哪个调度器上执行
 *
 * 每次调用了ObservableOn这个操作符时，之后都会在选择的调度器上进行观察，直到再次调用ObservableOn切换了调度器。
 */
package gordon.study.snippet.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Study02 {

    public static void main(String[] args) {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("Observable emit events in thread: " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                // 改变上游发送事件的线程，让它去子线程中发送事件
                .subscribeOn(Schedulers.newThread())
                // 如果不改变下游接收事件的线程，则下游默认使用和上游相同的线程接收事件。我们改为用计算线程接受事件。
                .observeOn(Schedulers.computation())
                // 该map操作就在计算线程中执行
                .map(val -> {
                    int ret = val + 10;
                    System.out.println("map " + val + " to " + ret + " in thread: " + Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(300);
                    return ret;
                })
                // 将接收事件的线程改为io线程。
                .observeOn(Schedulers.io())
                // 该map操作就在io线程中执行
                .map(val -> {
                    int ret = val + 100;
                    System.out.println("map " + val + " to " + ret + " in thread: " + Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(300);
                    return ret;
                })
                // 接收事件的线程依然为io线程
                .doOnNext(val -> System.out.println("Observer onNext: " + val + " in thread: " + Thread.currentThread().getName()))
                .subscribe();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
        }
    }
}
