/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/9b1304435564
 *
 * 上游变成了Flowable，下游变成了Subscriber，但是水管之间的连接还是通过subscribe()
 *
 * 创建Flowable的时候增加了一个参数，这个参数是用来选择背压，也就是出现上下游流速不均衡的时候应该怎么处理的办法,
 * 这里我们直接用BackpressureStrategy.ERROR这种方式，这种方式会在出现上下游流速不均衡的时候直接抛出一个异常，
 * 这个异常就是著名的MissingBackpressureException
 *
 * 下游的onSubscribe方法中传给我们的不再是Disposable了，而是Subscription。它们都是上下游中间的一个开关，
 * 之前我们说调用Disposable.dispose()方法可以切断水管，同样的调用Subscription.cancel()也可以切断水管，
 * 不同的地方在于Subscription增加了一个void request(long n)方法。
 *
 *
 *
 * Flowable在设计的时候采用了一种新的思路也就是**响应式拉取**的方式来更好的解决上下游流速不均衡的问题，当调用
 * Subscription.request(1)时，下游指明要消费一个事件，上游就发送一个事件给下游；再次调用request(10)，下游指明要消费十个事件，
 * 上游就发送十个事件给下游。所以我们把request当做是一种能力，当成下游处理事件的能力，下游能处理几个就告诉上游我要几个，
 * 这样只要上游根据下游的处理能力来决定发送多少事件，就不会造成一窝蜂的发出一堆事件来，从而导致OOM。
 * 这也就完美的解决之前我们所学到的两种方式的缺陷，过滤事件会导致事件丢失，减速又可能导致性能损失。而这种方式既解决了事件丢失的问题，又解决了速度的问题
 *
 * 但是显然，只有当上游正确的实现了如何根据下游的处理能力来发送事件的时候，才能达到这种效果，如果上游根本不管下游的处理能力，
 * 一股脑的瞎他妈发事件，仍然会产生上下游流速不均衡的问题。
 *
 */
package gordon.study.snippet.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Study07 {

    private static Subscription sub;

    public static void main(String[] args) throws InterruptedException {

        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                System.out.println("emit 1");
                emitter.onNext(1);
                System.out.println("emit 2");
                emitter.onNext(2);
                System.out.println("emit 3");
                emitter.onNext(3);
                System.out.println("emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
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
        };

        upstream.subscribe(downstream);

        Thread.sleep(1000);
        System.out.println("----------------------------------------------------");

        downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                // 当注释掉该行后，onError被回调，打印MissingBackpressureException
                // subscription.request(Long.MAX_VALUE);
                /*
                因为下游没有调用request，上游就认为下游没有处理事件的能力，而这又是一个同步的订阅，既然下游处理不了，
                那上游不可能一直等待吧，如果是这样，万一这两根水管工作在主线程里，界面不就卡死了吗，因此只能抛个异常来提醒我们。
                那如何解决这种情况呢，很简单啦，下游直接调用request(Long.MAX_VALUE)就行了，或者根据上游发送事件的数量来request就行了，
                比如这里request(3)就可以了。
                 */
                /*
                执行顺序应该是Flowable emit 事件1，因为上下游同一线程，接下来应该是Subscriber消费事件1，但是Subscriber没有request，
                因此抛出MissingBackpressureException，onError方法被回调。Flowable继续发射剩余事件。
                 */

                // subscription.request(1);
                /*
                如果request(1)，Flowable emit 的事件1会被正确消费掉，但是事件2会触发MissingBackpressureException，剩余同上。
                 */
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext: " + integer);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };

        upstream.subscribe(downstream);

        Thread.sleep(1000);
        System.out.println("----------------------------------------------------");

        upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 1; i < 6; i++) {
                    System.out.println("emit " + i);
                    emitter.onNext(i);
                }
                System.out.println("emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

        downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                /*
                 为什么上下游没有工作在同一个线程时，上游却正确的发送了所有的事件呢？这是因为在Flowable里默认有一个大小为128的水缸，
                 当上下游工作在不同的线程中时，上游就会先把事件发送到这个水缸中，因此，下游虽然没有调用request，但是上游在水缸中保存
                 着这些事件，只有当下游调用request时，才从水缸里取出事件发给下游。
                 */
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
        };

        upstream.subscribe(downstream);

        /*
        上游发送了五个事件保存到了水缸里，下游request几个, 就接收几个进行处理。
        消费序列：1 -- 2 and 3 -- 4
         */
        sub.request(1);
        Thread.sleep(500);
        sub.request(2);
        Thread.sleep(500);
        sub.request(1);

        Thread.sleep(1000);
        System.out.println("----------------------------------------------------");

        upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                /*
                上游一次性发送了130个事件（超过128的缓冲区大小），下游一个也不接收，抛MissingBackpressureException
                 */
                for (int i = 0; i < 130; i++) {
                    emitter.onNext(i);
                }
                System.out.println("emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

        downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
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
        };

        upstream.subscribe(downstream);

        Thread.sleep(1000);
    }
}
