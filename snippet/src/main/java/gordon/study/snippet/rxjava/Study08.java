/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/a75ecf461e02
 *
 *
 */
package gordon.study.snippet.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Study08 {

    private static Subscription sub;

    public static void main(String[] args) throws InterruptedException {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10000; i++) {
                    if (i % 1000 == 0) {
                        System.out.println("emit " + i);
                    }
                    emitter.onNext(i);
                }
                System.out.println("emit complete");
                emitter.onComplete();
            }
            /*
            ERROR: 见前篇描述。默认128缓冲区，溢出抛MissingBackpressureException异常
            BUFFER: 无限大缓冲区，类似于Observable，需注意内存占用
            DROP: 缓冲区满后，事件被抛弃
            LATEST: 缓冲区队列满后，额外保存最新的事件
            MISSING:
             */
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        sub = subscription;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }

                });

        Thread.sleep(100);
        sub.request(140);
        /*
        BUFFER: 0-139
        DROP: 0-127
        LATEST: 0-127 & 9999
         */
        Thread.sleep(1000);
    }
}
