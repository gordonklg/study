package gordon.study.snippet.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeakyBucketRateLimit {

    private static final double CAPACITY = 6.0;

    private static final double RATE = 2.0; // 2 reqs per second

    private static double waterLevel;

    private static long lastAccessTime;

    public static synchronized boolean allowAccess() {
        // calc current water level
        long now = System.currentTimeMillis();
        waterLevel -= (now - lastAccessTime) * RATE / 1000;
        if (waterLevel < 0) {
            waterLevel = 0;
        }
        lastAccessTime = now;
        if (waterLevel <= CAPACITY - 1) {
            waterLevel += 1;
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.submit(() -> {
                while (true) {
                    if (LeakyBucketRateLimit.allowAccess()) {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(100);
                    }
                }
            });
        }

    }

}
