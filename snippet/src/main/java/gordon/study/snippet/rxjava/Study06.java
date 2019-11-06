/**
 * 给初学者的RxJava2.0教程
 * https://www.jianshu.com/p/e4c6d7989356
 *
 */
package gordon.study.snippet.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Study06 {

    public static void main(String[] args) {

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        })
                // sample 取样，取每个时间段内最新的一个（如果存在）。相当于将上游限速到每秒一个事件（多余事件被抛弃）。
                // 线程sleep 1秒也能防止当前observable产生过多事件。
                .sample(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io());

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
