package gordon.study.concurrent.scene.criticalsection;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock {

    private static long a;
    private static long b;

    private static ReentrantLock lock = new ReentrantLock();

    public static void trade(int val) {
        lock.lock();
        a -= val;
        b += val;
        lock.unlock();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        Future[] future = new Future[10];
        for (int i = 0; i < 10; i++) {
            future[i] = es.submit(() -> {
                Random r = new Random(System.currentTimeMillis() + Thread.currentThread().getName().hashCode());
                for (int j = 0; j < 10000; j++) {
                    trade(r.nextInt(11) - 5);
                }
            });
        }
        for (Future f : future) {
            f.get();
        }
        System.out.printf("a=%d, b=%d", a, b);
        es.shutdown();
    }
}
