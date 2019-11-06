/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/128e662906af
 *
 * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里
 *
 * 再找一份参考资料：https://www.jianshu.com/p/f67e05d7cd30
 * concatMap和flatMap的功能是一样的，将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据放进一个单独的Observable。
 * 只不过最后合并Observables flatMap采用的merge，而concatMap采用的是连接(concat)。总之一句一话,他们的区别在于：
 * concatMap是有序的，flatMap是无序的，concatMap最终输出的顺序与原序列保持一致，而flatMap则不一定，有可能出现交错。
 */
package gordon.study.snippet.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Study03 {

    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(val -> {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                list.add("I am value " + val + " in thread " + Thread.currentThread().getName());
            }
            // 原上游Observable的每个事件转化为一个新的Observable
            // 最后flatMap会把这些新的Observables发射的事件合并到一个单独的Observable中（即flatMap方法返回的对象）
            return Observable.fromIterable(list);
        }).subscribe(val -> System.out.println("onNext:" + val));

        Observable.create(new ObservableOnSubscribe<Integer[]>() {
            @Override
            public void subscribe(ObservableEmitter<Integer[]> emitter) throws Exception {
                emitter.onNext(new Integer[]{1, 2, 3, 4});
                emitter.onNext(new Integer[]{5, 6});
                emitter.onNext(new Integer[]{7, 8, 9});
            }
        })
                .flatMap(val -> Observable.fromArray(val).delay(10 * val.length, TimeUnit.MILLISECONDS))
                .subscribe(val -> System.out.println("flatMap onNext:" + val));

        Observable.create(new ObservableOnSubscribe<Integer[]>() {
            @Override
            public void subscribe(ObservableEmitter<Integer[]> emitter) throws Exception {
                emitter.onNext(new Integer[]{1, 2, 3, 4});
                emitter.onNext(new Integer[]{5, 6});
                emitter.onNext(new Integer[]{7, 8, 9});
            }
        })
                .concatMap(val -> Observable.fromArray(val).delay(100 + 10 * val.length, TimeUnit.MILLISECONDS))
                .subscribe(val -> System.out.println("concatMap onNext:" + val));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}
