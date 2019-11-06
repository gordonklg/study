/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/0f2d6c2387c9
 *
 * 运行指定 -Xmx20m
 * 用jmap -heap查看堆，发现堆空间已被用完。但是和教程不一样的是并不会抛OOM异常！
 *
 */
package gordon.study.snippet.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Study05 {

    public static void main(String[] args) {

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                while (true) {
                    emitter.onNext("A");
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, (a, b) -> a + b)
                .observeOn(Schedulers.io())
                .doOnNext(val -> System.out.println("onNext: " + val))
                .subscribe();

        LockSupport.park();
    }
}
