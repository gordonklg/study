/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/36e0f7f43a51
 *
 * 上一节的例子，上游并没有按照下游的实际消费情况调整生产事件的速度。如果想要达到生产者消费者模式（缓冲区满后生产者不再生产事件），怎么做？
 * FlowableEmitter 有个 requested() 方法，可以获悉当前还需要生产多少个事件。
 *
 * 同步模式下（上下游在同一个线程）非常好理解，一开始 requested 返回值是0，下游 request 了多少事件，requested 值就是多少。
 * 当 FlowableEmitter 通过 onNext 发送事件时，该值减一。
 *
 * 异步模式下，每个线程里都有一个 requested，上游中的 requested 的值是由 RxJava 内部调用 request(n) 去设置的。
 * 下游在一开始就在内部调用了request(128)去设置了上游中的值，因此即使下游没有调用request()，上游也能发送128个事件，
 * 这也可以解释之前我们为什么说 Flowable 中默认的水缸大小是128，其实就是这里设置的。
 * 源码：FlowableObserveOn onSubscribe方法中 s.request(prefetch)，prefetch 默认值为128.
 * 源码：prefetch 默认值在 Flowable 中定义，static final int BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128);
 * 其中rx2.buffer-size是系统参数。对于上一节的例子，如果设置-Drx2.buffer-size=256，则缓冲区成功变大为256。
 *
 * RxJava 内部什么时候调用上游线程的 request(n) 去设置 requested 呢？当下游的 request 数量超过96时触发。
 * 源码：StrictSubscriber 的 request 方法 -> BaseObserveOnSubscriber 的 request 方法 -> FlowableObserveOn$ObserveOnSubscriber 的 runAsync 方法，
 * 可知，当达到 limit 数量时，worker 线程调用 upstream 的 request 方法，更新上游的 requested 值。（一般情况下，调用上游 request 方法传入的参数值就是 limit 的值）
 * 源码：limit 在 FlowableObserveOn$BaseObserveOnSubscriber 中定义，
 * limit = prefetch - (prefetch >> 2);
 * 因此，如果 limit 是 2 的 n 次方，limit 就是 prefetch 的 3/4，即，默认 limit = 128 * 3 / 4 = 96
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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Study09 {

    private static Subscription sub;

    public static void main(String[] args) throws InterruptedException {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                int count = 0;
                long requested = 0;
                while (!emitter.isCancelled()) {
                    requested = emitter.requested();
                    if (requested > 0) {
                        System.out.println("Print requested: " + requested);
                        emitter.onNext(count++);
                    }
                }
            }
        }, BackpressureStrategy.ERROR)
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

        Thread.sleep(1000);
        System.out.println("------------------------------");
        sub.request(95);
        /*
        请求消费95个事件，未达到96阈值，因此上游 requested 值未更新任为0
         */

        Thread.sleep(1000);
        System.out.println("------------------------------");
        sub.request(5);
        /*
        再消费5个，累计超过96阈值，因此 RxJava 内部对上游线程调用 request(96)
         */

        Thread.sleep(1000);
    }
}
