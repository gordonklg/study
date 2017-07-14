package gordon.study.cache.ehcache3.springcache;

import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gordon.study.cache.springcache.UserService;

public class SpringCacheEhcache3Test {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_cache_ehcache3.xml");
        UserService s = (UserService) context.getBean("userService");
        JCacheCacheManager m = (JCacheCacheManager) context.getBean("cacheManager");

        System.out.println(s.findUser("id1").getInfo()); // query user in database by id: id1
        System.out.println(s.findUser("id2").getInfo()); // query user in database by id: id2
        System.out.println(s.findUser("id1").getInfo()); // by cache
        s.updateUser("id2", "hello"); // update user in database by id: id2
        System.out.println(s.findUser("id2").getInfo()); // by cache
        s.updateUser("id3", "ni hao"); // update user in database by id: id3
        System.out.println(s.findUser("id3").getInfo()); // by cache
        s.deleteUser("id1"); // delete user in database by id: id1
        System.out.println(m.getCache("userCache").getNativeCache().getClass()); // {id2, id3}
        System.out.println(s.findUser("nonexistence")); // query user in database by id: nonexistence
        System.out.println(s.findUser("nonexistence")); // by cache
        System.out.println(m.getCache("userCache").getNativeCache()); // {id2, id3, nonexistence(org.springframework.cache.support.NullValue)}

        context.close();
    }
}
