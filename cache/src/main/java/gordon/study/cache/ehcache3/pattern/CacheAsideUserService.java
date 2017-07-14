package gordon.study.cache.ehcache3.pattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

import gordon.study.cache.ehcache3.basic.UserModel;

public class CacheAsideUserService {

    private UserManagedCache<String, UserModel> cache;

    public CacheAsideUserService() {
        cache = UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, UserModel.class).build(true);
    }

    public UserModel findUser(String id) {
        UserModel cached = cache.get(id);
        if (cached != null) {
            System.out.println("get user from cache");
            return cached;
        }
        UserModel user = new UserModel(id, "info ..."); // find user
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("get user from db");
        cache.put(id, user);
        return user;
    }

    public UserModel updateUser(String id, String info) {
        UserModel user = new UserModel(id, info); // update user
        cache.put(id, user);
        return user;
    }

    public boolean deleteUser(String id) {
        // delete user
        cache.remove(id);
        return true;
    }

    public static void main(String[] args) {
        final CacheAsideUserService service = new CacheAsideUserService();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    service.findUser("1");
                }
            });
        }
        executorService.shutdown();
    }
}
