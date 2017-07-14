package gordon.study.cache.ehcache3.pattern;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

import gordon.study.cache.ehcache3.basic.UserModel;

public class SyncByIdCacheAsideUserService {

    private UserManagedCache<String, UserModel> cache;

    private final Map<String, ReentrantLock> lockMap = new WeakHashMap<>();

    private final ReentrantLock lockMapLock = new ReentrantLock();

    public SyncByIdCacheAsideUserService() {
        cache = UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, UserModel.class).build(true);
    }

    public UserModel findUser(String id) {
        UserModel result = cache.get(id);
        if (result == null) {
            lockMapLock.lock();
            ReentrantLock lock = lockMap.get(id);
            if (lock == null) {
                lock = new ReentrantLock();
                lockMap.put(new String(id), lock);
            }
            lockMapLock.unlock();

            lock.lock();
            result = cache.get(id);
            if (result == null) {
                result = new UserModel(id, "info ..."); // find user
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println("get user from db: " + id);
                cache.put(id, result);
            } else {
                System.out.println("get user from cache after competition: " + id);
            }
            lock.unlock();
        } else {
            System.out.println("get user from cache: " + id);
        }
        return result;
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

    public static void main(String[] args) throws Exception {
        final SyncByIdCacheAsideUserService service = new SyncByIdCacheAsideUserService();
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
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.gc();
        Thread.sleep(1000);
        System.out.println(service.lockMap.size());
    }
}
