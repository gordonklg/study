package gordon.study.cache.caffeine.basic;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class ManualCaffeine {
    public static void main(String[] args) {
        Cache<Integer, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();

        // Lookup an entry, or null if not found
        String value = cache.getIfPresent(1);
        System.out.println("value should be null: " + value);

        // Insert or update an entry
        cache.put(1, "wrong");
        System.out.println("value should be wrong: " + cache.getIfPresent(1));
        cache.put(1, "one");
        System.out.println("value should be one: " + cache.getIfPresent(1));

        // Lookup and compute an entry if absent, or null if not computable
        value = cache.get(2, key -> createExpensiveValue(key));
        System.out.println("value should be two: " + value);

        // Remove an entry
        cache.invalidate(2);
        System.out.println("value should be null: " + cache.getIfPresent(2));
    }

    private static String createExpensiveValue(Integer key) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "two";
    }
}
