package gordon.study.concurrent.geekbang.u14lockcondition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLockTest {

    private static final Lock rtl = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            rtl.lock();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            } finally {
                rtl.unlock();
            }
        }).start();

        Runnable r = () -> {
            rtl.lock();
            try {
                System.out.println(Thread.currentThread().getName());
            } finally {
                rtl.unlock();
            }
        };
        for (int i = 1; i < 10; i++) {
            Thread t = new Thread(r, "QueuedThread-" + i);
            Thread.sleep(100);
            t.start();
        }
    }
}
