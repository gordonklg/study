package gordon.study.concurrent.scene.producerconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock implements Storage {

    private static final int BUF_SZ = 10;

    private int[] buffer = new int[BUF_SZ];

    private int pos;

    private ReentrantLock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition noteEmpty = lock.newCondition();

    @Override
    public void produce(int val) throws InterruptedException {
        lock.lock();
        try {
            while (pos == BUF_SZ) {
                notFull.await();
            }
            buffer[pos++] = val;
            noteEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int consume() throws InterruptedException {
        int val = 0;
        lock.lock();
        try {
            while (pos == 0) {
                noteEmpty.await();
            }
            val = buffer[--pos];
            notFull.signal();
            return val;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getSize() {
        return pos;
    }

    public static void main(String[] args) throws Exception {
        UseReentrantLock storage = new UseReentrantLock();
        storage.run();
    }

}

// *   public void put(Object x) throws InterruptedException {
//        *     <b>lock.lock();
//        *     try {</b>
//        *       while (count == items.length)
//        *         <b>notFull.await();</b>
//        *       items[putptr] = x;
//        *       if (++putptr == items.length) putptr = 0;
//        *       ++count;
//        *       <b>notEmpty.signal();</b>
//        *     <b>} finally {
//        *       lock.unlock();
//        *     }</b>
//        *   }