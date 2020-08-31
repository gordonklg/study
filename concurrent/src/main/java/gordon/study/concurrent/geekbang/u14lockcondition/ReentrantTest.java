package gordon.study.concurrent.geekbang.u14lockcondition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTest {

    private final Lock rtl = new ReentrantLock();

    int value;

    public int get() {
        // 获取锁
        rtl.lock();
        try {
            return value;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }

    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value = 1 + get();
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantTest instance = new ReentrantTest();
        instance.addOne();
        System.out.println(instance.value);
    }
}