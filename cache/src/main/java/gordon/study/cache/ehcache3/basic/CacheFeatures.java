package gordon.study.cache.ehcache3.basic;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.EvictionAdvisor;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

public class CacheFeatures {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) throws Exception {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        CacheConfiguration config = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class, String.class, ResourcePoolsBuilder.heap(5))
                .withEvictionAdvisor(new TopThreeAreImportantEvictionAdvisor())
                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(3, TimeUnit.SECONDS))).build();
        Cache myCache = cacheManager.createCache("myCache", config);
        try { // key type 错误
            myCache.put("Invalid key type", "sad");
        } catch (Exception e) {
            System.out.println("Invalid key type");
        }
        try { // value type 错误
            myCache.put(1L, 9527L);
        } catch (Exception e) {
            System.out.println("Invalid value type");
        }
        for (int i = 1; i <= 10; i++) { // 超出数量上限，回收策略开始生效：key为1、2、3的条目不被回收
            myCache.put(i, "No. " + i);
        }
        printAllCacheEntries(myCache); // contains 1,2,3 and other two keys
        System.out.println();

        for (int i = 0; i < 3; i++) { // 等待3秒，3秒内一直没被使用的条目全部过期移除。只有key为1的条目还在。
            myCache.get(1);
            Thread.sleep(1050);
        }
        printAllCacheEntries(myCache); // only key=1 in cache
    }

    private static void printAllCacheEntries(Cache<Integer, String> cache) {
        cache.forEach(new Consumer<Cache.Entry<Integer, String>>() {

            @Override
            public void accept(Cache.Entry<Integer, String> entry) {
                System.out.print(entry.getKey() + " ");
            }
        });
    }

    private static class TopThreeAreImportantEvictionAdvisor implements EvictionAdvisor<Integer, String> {

        @Override
        public boolean adviseAgainstEviction(Integer key, String value) {
            return (key.intValue() < 4);
        }
    }
}
