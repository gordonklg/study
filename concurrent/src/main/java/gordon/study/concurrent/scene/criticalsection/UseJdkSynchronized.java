package gordon.study.concurrent.scene.criticalsection;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UseJdkSynchronized {

    private static long a;
    private static long b;

    public static synchronized void trade(int val) {
        a -= val;
        b += val;
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
