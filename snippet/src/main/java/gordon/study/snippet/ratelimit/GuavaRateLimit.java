package gordon.study.snippet.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaRateLimit {

    private static final RateLimiter rateLimiter = RateLimiter.create(2.0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.submit(() -> {
                while (true) {
                    if (rateLimiter.tryAcquire()) {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(100);
                    }
                }
            });
        }

    }

}
