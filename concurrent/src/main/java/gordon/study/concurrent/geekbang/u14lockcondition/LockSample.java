package gordon.study.concurrent.geekbang.u14lockcondition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockSample {

    private final Lock rtl = new ReentrantLock();

    int value;

    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value += 1;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }

    public static void main(String[] args) {
        LockSample instance = new LockSample();
        instance.addOne();
        System.out.println(instance.value);
    }
}
