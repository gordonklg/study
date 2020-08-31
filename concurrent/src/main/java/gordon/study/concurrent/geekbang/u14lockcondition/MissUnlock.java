package gordon.study.concurrent.geekbang.u14lockcondition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MissUnlock {

    private final Lock rtl = new ReentrantLock();

    int value;

    public void doSomething() {
        System.out.printf("%s starts do sth.\n", Thread.currentThread().getName());
        rtl.lock();
        System.out.printf("%s gets the lock.\n", Thread.currentThread().getName());
        value /= 0;
        rtl.unlock();
        System.out.printf("%s releases the lock.\n", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MissUnlock instance = new MissUnlock();
        Thread t1 = new Thread(()->{
            instance.doSomething();
        });
        t1.start();
        Thread t2 = new Thread(()->{
            instance.doSomething();
        });
        t2.start();
        // 某个线程会永远拿不到 rtl lock，永远处于 WAITING (parking) 状态
    }
}
