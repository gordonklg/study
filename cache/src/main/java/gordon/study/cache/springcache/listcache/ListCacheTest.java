package gordon.study.cache.springcache.listcache;

import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ListCacheTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_cache_simple.xml");
        ListCacheUserService service = (ListCacheUserService) context.getBean("listCacheUserService");
        SimpleCacheManager cm = (SimpleCacheManager) context.getBean("cacheManager");

        System.out.println(service.findUser("id1").getInfo()); // query user in database by id: id1
        System.out.println(cm.getCache("userCache").getNativeCache()); // {iid1, eemail1, pphone1}
        System.out.println(service.findUser("id1").getInfo()); // by cache
        System.out.println(service.findUserByEmail("email1").getInfo()); // by cache

        System.out.println(service.findUserByEmail("email2").getInfo()); // query user in database by email: email2
        System.out.println(cm.getCache("userCache").getNativeCache()); // {iid1, iid2, eemail1, eemail2, pphone1, pphone2}
        System.out.println(service.findUser("id2").getInfo()); // by cache

        System.out.println(service.findUserByPhone("phone8").getInfo()); // query user in database by email: email2
        System.out.println(cm.getCache("userCache").getNativeCache()); // {iid1, iid2, iid8, eemail1, eemail2, pphone1, pphone2, pphone8}

        System.out.println(service.findUser("nonexistence")); // query user in database by id: nonexistence
        System.out.println(cm.getCache("userCache").getNativeCache()); // {inonexistence, iid1, iid2, iid8, eemail1, eemail2, pphone1, pphone2, pphone8}

        System.out.println(service.findUserByEmail("nonexistence")); // query user in database by email: nonexistence
        System.out.println(cm.getCache("userCache").getNativeCache()); // {inonexistence, enonexistence, id1, id2, id8, eemail1, eemail2, pphone1, pphone2, pphone8}

        service.updateUser("id1", "new info"); // update user in database by id: id1
        System.out.println(service.findUser("id1").getInfo()); // by cache
        
        service.deleteUser("id1"); // delete user in database by id: id1
        System.out.println(cm.getCache("userCache").getNativeCache()); // {inonexistence, enonexistence, id2, id8, eemail2, pphone2, pphone8}

        service.deleteUser("id8"); // delete user in database by id: id8
        System.out.println(cm.getCache("userCache").getNativeCache()); // {inonexistence, enonexistence, id2, eemail2, pphone2}

        service.deleteUser("faint"); // delete user in database by id: faint
        System.out.println(cm.getCache("userCache").getNativeCache()); // {inonexistence, enonexistence, id2, eemail2, pphone2}

        service.allUsers();
        System.out.println(cm.getCache("userCache").getNativeCache());
        context.close();
    }
}
