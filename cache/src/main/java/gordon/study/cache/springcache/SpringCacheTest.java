package gordon.study.cache.springcache;

import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCacheTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_cache_simple.xml");
        UserService service = (UserService) context.getBean("userService");
        SimpleCacheManager cm = (SimpleCacheManager) context.getBean("cacheManager");

        System.out.println(service.findUser("id1").getInfo()); // query user in database by id: id1
        System.out.println(service.findUser("id2").getInfo()); // query user in database by id: id2
        System.out.println(service.findUser("id1").getInfo()); // by cache
        service.updateUser("id2", "hello"); // update user in database by id: id2
        System.out.println(service.findUser("id2").getInfo()); // by cache
        service.updateUser("id3", "ni hao"); // update user in database by id: id3
        System.out.println(service.findUser("id3").getInfo()); // by cache
        service.deleteUser("id1"); // delete user in database by id: id1
        System.out.println(cm.getCache("userCache").getNativeCache()); // {id2, id3}
        System.out.println(service.findUser("nonexistence")); // query user in database by id: nonexistence
        System.out.println(service.findUser("nonexistence")); // by cache
        System.out.println(cm.getCache("userCache").getNativeCache()); // {id2, id3, nonexistence(org.springframework.cache.support.NullValue)}

        context.close();
    }
}
