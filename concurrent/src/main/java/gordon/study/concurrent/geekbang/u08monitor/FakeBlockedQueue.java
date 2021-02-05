package gordon.study.concurrent.geekbang.u08monitor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FakeBlockedQueue<T> {

    private final Lock lock = new ReentrantLock();
    // 条件变量：队列不满
    private final Condition notFull = lock.newCondition();
    // 条件变量：队列不空
    private final Condition notEmpty = lock.newCondition();

    private List<T> store = new LinkedList<T>();

    private int size = 0;

    // 入队
    void enq(T x) {
        lock.lock();
        try {
            while (size == 100) {
                // 等待队列不满
                notFull.await();
            }
            store.add(0, x);
            size++;
            //入队后,通知可出队
            notEmpty.signal();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    // 出队
    void deq() {
        lock.lock();
        try {
            while (size == 0) {
                // 等待队列不空
                notEmpty.await();
            }
            store.remove(--size);
            //出队后，通知可入队
            notFull.signal();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }
}