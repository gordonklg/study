package gordon.study.cache.ehcache3.pattern;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import gordon.study.cache.ehcache3.basic.UserModel;
import gordon.study.cache.ehcache3.basic.UserModel.NullUser;

public class CacheThroughUserService {

    private UserManagedCache<String, UserModel> cache;

    public CacheThroughUserService() {
        cache = UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, UserModel.class).withLoaderWriter(new CustomLoaderWriter())
                .build(true);
    }

    public UserModel findUser(String id) {
        UserModel result = cache.get(id);
        if (result == null) {
            System.out.println("can't find user in cache!");
        } else {
            System.out.println("get user from cache: " + id);
        }
        return result;
    }

    public UserModel updateUser(String id, String info) {
        UserModel user = new UserModel(id, info);
        cache.put(id, user);
        return user;
    }

    public boolean deleteUser(String id) {
        cache.remove(id);
        return true;
    }

    public static void main(String[] args) throws Exception {
        final CacheThroughUserService service = new CacheThroughUserService();
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 3; i++) {
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
//            executorService.execute(new Runnable() {
//                public void run() { service.findUser("34"); } // in the same bucket with id=1, so it blocked.
//            });
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.HOURS);
        System.out.println("---------------------------------");
        service.updateUser("1", "new info ...");
        service.findUser("1");
        service.deleteUser("1");
        service.findUser("1");
    }

    private static class CustomLoaderWriter implements CacheLoaderWriter<String, UserModel> {

        @Override
        public UserModel load(String key) throws Exception {
            System.out.println("::load user by id: " + key);
            Thread.sleep(1000);
            if (key == null || key.equals("1")) {
                return new NullUser();
            }
            return new UserModel(key, "info ...");
        }

        @Override
        public Map<String, UserModel> loadAll(Iterable<? extends String> keys) throws BulkCacheLoadingException, Exception {
            System.out.println("::load user by ids: " + keys);
            return null;
        }

        @Override
        public void write(String key, UserModel value) throws Exception {
            System.out.println("::update user by id: " + key);
        }

        @Override
        public void writeAll(Iterable<? extends Entry<? extends String, ? extends UserModel>> entries)
                throws BulkCacheWritingException, Exception {
            System.out.println("::update user by ids");
        }

        @Override
        public void delete(String key) throws Exception {
            System.out.println("::delete user by id: " + key);
        }

        @Override
        public void deleteAll(Iterable<? extends String> keys) throws BulkCacheWritingException, Exception {
            System.out.println("::delete user by ids");
        }
    }
}
