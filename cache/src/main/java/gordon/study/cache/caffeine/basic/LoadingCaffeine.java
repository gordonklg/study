package gordon.study.cache.caffeine.basic;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoadingCaffeine {
    public static void main(String[] args) {
        LoadingCache<Integer, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build(key -> createExpensiveValue(key));

        // Lookup and compute an entry if absent, or null if not computable
        String value = cache.get(1);
        System.out.println("value should be one: " + value);

        // Lookup and compute entries that are absent
        List<Integer> keys = new ArrayList<>(2);
        keys.add(1);
        keys.add(2);
        keys.add(3);
        Map<Integer, String> map = cache.getAll(keys);
        System.out.println(map);
    }

    private static String createExpensiveValue(Integer key) {
        System.out.println("try to get value of: " + key);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (key == 1) {
            return "one";
        } else if (key == 2) {
            return "two";
        } else {
            return "number";
        }
    }
}
