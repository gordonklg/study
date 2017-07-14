package gordon.study.cache.springcache.pattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import gordon.study.cache.ehcache3.basic.UserModel;

@Service
@CacheConfig(cacheNames = "userCache")
public class SyncCacheAsideUserService {

    @Cacheable(sync = true)
    public UserModel findUser(String id) {
        UserModel user = new UserModel(id, "info ..."); // find user
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("get user from db: " + id);
        return user;
    }

    @CachePut
    public UserModel updateUser(String id, String info) {
        UserModel user = new UserModel(id, info); // update user
        return user;
    }

    @CacheEvict
    public boolean deleteUser(String id) {
        return true;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring_cache_simple.xml");
        SyncCacheAsideUserService service = (SyncCacheAsideUserService) context.getBean("syncCacheAsideUserService");
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    service.findUser("1");
                }
            });
            executorService.execute(new Runnable() {
                public void run() {
                    service.findUser("2");
                }
            });
            executorService.execute(new Runnable() {
                public void run() {
                    service.findUser("3");
                }
            });
        }
        executorService.shutdown();
        context.close();
    }
}
