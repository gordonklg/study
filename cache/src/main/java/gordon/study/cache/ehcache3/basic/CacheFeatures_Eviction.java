package gordon.study.cache.ehcache3.basic;

import java.util.BitSet;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.EvictionAdvisor;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheFeatures_Eviction {

    private static final int CACHE_SIZE = 50;

    public static void main(String[] args) throws Exception {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        CacheConfiguration<Integer, String> config = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class, String.class, ResourcePoolsBuilder.heap(50))
                .withEvictionAdvisor(new MyEvictionAdvisor()).build();
        Cache<Integer, String> myCache = cacheManager.createCache("myCache", config);
        for (int i = 1; i <= CACHE_SIZE; i++) {
            myCache.put(i, "No. " + i);
            Thread.sleep(10);
        }
        myCache.put(CACHE_SIZE + 1, "No. " + (CACHE_SIZE + 1));

        BitSet bitSet = new BitSet(CACHE_SIZE + 1);
        for (Cache.Entry<Integer, String> entry : myCache) {
            bitSet.set(entry.getKey() - 1);
        }
        System.out.println("Eviction number: " + (bitSet.nextClearBit(0) + 1));
    }

    private static class MyEvictionAdvisor implements EvictionAdvisor<Integer, String> {

        @Override
        public boolean adviseAgainstEviction(Integer key, String value) {
            return (key.intValue() <= CACHE_SIZE); // 最后一条是可回收条目，则必定回收这一条
            // return (key.intValue() <= (CACHE_SIZE + 1)); // 最后一条是不建议回收条目，则随机回收一条
        }
    }
}
