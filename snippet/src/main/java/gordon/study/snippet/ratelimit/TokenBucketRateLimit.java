package gordon.study.snippet.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TokenBucketRateLimit {

    private static final double CAPACITY = 6.0;

    private static final double RATE = 2.0; // 2 tokens per second

    private static double tokens;

    private static long lastAccessTime;

    public static synchronized boolean allowAccess() {
        // calc current tokens in bucket
        long now = System.currentTimeMillis();
        tokens += (now - lastAccessTime) * RATE / 1000;
        if (tokens > CAPACITY) {
            tokens = CAPACITY;
        }
        lastAccessTime = now;
        if (tokens < 1) {
            return false;
        }
        tokens -= 1;
        return true;
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.submit(() -> {
                while (true) {
                    if (TokenBucketRateLimit.allowAccess()) {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(100);
                    }
                }
            });
        }

    }

}
