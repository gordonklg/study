package gordon.study.concurrent.geekbang.u14lockcondition.simulatedubbo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture {
    // 创建锁与条件变量
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();

    private String response;

    // 调用方通过该方法等待结果
    public String get(long timeout) throws TimeoutException {
        long start = System.currentTimeMillis();
        lock.lock();
        try {
            while (!isDone()) {
                try {
                    done.await(timeout, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    // just ignore
                }
                long cur = System.currentTimeMillis();
                if (isDone() || cur - start > timeout) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
        if (!isDone()) {
            throw new TimeoutException();
        }
        return response;
    }

    // RPC结果是否已经返回
    private boolean isDone() {
        return response != null;
    }

    // RPC结果返回时调用该方法
    public void doReceived(String res) {
        lock.lock();
        try {
            response = res;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void request(MockServer server) {
        server.request(this);
    }

}