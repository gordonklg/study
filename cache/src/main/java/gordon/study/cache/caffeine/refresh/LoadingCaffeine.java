package gordon.study.cache.caffeine.refresh;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadingCaffeine {
    public static void main(String[] args) throws Exception {
        LoadingCache<Integer, String> cache = Caffeine.newBuilder()
                .refreshAfterWrite(8, TimeUnit.SECONDS)
                .maximumSize(10_000)
                .build(key -> createExpensiveValue(key));

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". want to get cache of 1.");
                    String value = cache.get(1);
                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". cache value of 1: " + value);
                }
            });
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". want to get cache of 2.");
                    String value = cache.get(2);
                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". cache value of 2: " + value);
                }
            });
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". want to get cache of 3.");
                    String value = cache.get(3);
                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". cache value of 3: " + value);
                }
            });
//            executorService.execute(new Runnable() {
//                public void run() {
//                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". want to get cache of 4.");
//                    String value = cache.get(4);
//                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". cache value of 4: " + value);
//                }
//            });
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.HOURS);
    }

    private static String createExpensiveValue(Integer key) {
        System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ". try to get value of: " + key);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (key == 1) {
            return "one";
        } else if (key == 2) {
            return "two";
        } else  if (key == 3) {
            return "three";
        } else {
            return "number";
        }
    }
}
