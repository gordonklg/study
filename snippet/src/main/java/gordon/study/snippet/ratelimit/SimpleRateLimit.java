package gordon.study.snippet.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleRateLimit {

    private static final int PERIOD = 3000;

    private static final int LIMIT = 6;

    private static int counter;

    private static long startTime;

    public static synchronized boolean allowAccess() {
        long now = System.currentTimeMillis();
        if (startTime < now - PERIOD) {
            startTime = now;
            counter = 0;
        }
        return counter++ < LIMIT;
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.submit(() -> {
                while (true) {
                    if (SimpleRateLimit.allowAccess()) {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(100);
                    }
                }
            });
        }

    }

}
