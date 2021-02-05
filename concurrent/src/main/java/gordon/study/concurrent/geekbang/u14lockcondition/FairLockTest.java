package gordon.study.concurrent.geekbang.u14lockcondition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、公平锁能保证：老的线程排队使用锁，新线程仍然排队使用锁。
 * 2、非公平锁保证：老的线程排队使用锁；但是无法保证新线程抢占已经在排队的线程的锁。
 */
public class FairLockTest {

    private static final Lock rtl = new ReentrantLock(false);

    private static boolean allDone = false;

    public static void main(String[] args) throws InterruptedException {
        Thread starter = new Thread(() -> { // 第一个启动的线程，抢锁，给后面的线程排队的机会
            rtl.lock();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            } finally {
                rtl.unlock();
            }
        });
        starter.start();

        Runnable r = () -> {
            rtl.lock();
            try {
                String tName = Thread.currentThread().getName();
                System.out.println(tName);
                if (tName.endsWith("9")) {
                    allDone = true;
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
            } finally {
                rtl.unlock();
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(r, "QueuedThread-" + i);
            Thread.sleep(10); // 10个主要观察的线程，轮流启动
            t.start();
        }

        starter.join();
        Thread.sleep(10);

        while (allDone == false) { // 尽力创建新线程去尝试插队
            new Thread(() -> {
                rtl.lock();
                try {
                    if (allDone == false) {
                        System.out.println("bull win!!!" + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                } finally {
                    rtl.unlock();
                }
            }).start();
        }
    }
}
