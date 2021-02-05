package gordon.study.concurrent.geekbang.u09thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 就算线程被 stop 也会执行 finally 方法块
// 用 synchronized 也一样会释放锁
public class StopTest {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        StopTest instance = new StopTest();
        Thread th1 = new Thread(() -> {
            lock.lock();
            try {
                long accu = 0;
                while (Math.abs(1) > 0) {
                    accu++;
                }
                System.out.println("never print this line!!!");
            } finally {
                System.out.println("unlocking...");
                lock.unlock();
            }
        });
        Thread th2 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("in thread 2");
            } finally {
                lock.unlock();
            }
        });
        th1.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        th1.stop();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        th2.start();
        System.out.println("the end");
    }
}
