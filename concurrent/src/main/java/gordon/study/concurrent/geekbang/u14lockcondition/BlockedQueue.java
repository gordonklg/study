package gordon.study.concurrent.geekbang.u14lockcondition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue<E> {

    private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();

    private final Condition notEmpty = lock.newCondition();

    private Object[] items = new Object[10];

    private int count;

    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[count++] = e;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            E retVal = (E) items[--count];
            notFull.signal();
            return retVal;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockedQueue<Integer> queue = new BlockedQueue<>();
        new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    queue.put(i);
                    TimeUnit.NANOSECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            int sum = 0;
            try {
                for (int i = 0; i < 1000; i++) {
                    sum += queue.take();
                    TimeUnit.NANOSECONDS.sleep(50 + i / 100);
                }
                System.out.println(sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
