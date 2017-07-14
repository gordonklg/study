package gordon.study.cache.ehcache3.basic;

import java.net.URL;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

public class XmlConfig {

    public static void main(String[] args) {
        final URL myUrl = XmlConfig.class.getResource("/ehcache3_basic.xml");
        Configuration xmlConfig = new XmlConfiguration(myUrl);
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        cacheManager.init();
        Cache<String, UserModel> phoneUserCache = cacheManager.getCache("phoneUserCache", String.class, UserModel.class);
        UserModel user = new UserModel("user0001", "13316619988", "abc@123.com", "openid12345", "very very long user information ...");
        phoneUserCache.put(user.getPhone(), user);
        UserModel cached = phoneUserCache.get(user.getPhone());
        System.out.println(cached.getInfo());
    }
}
