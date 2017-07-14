package gordon.study.cache.ehcache3.pattern;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.ehcache.ValueSupplier;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.core.Ehcache;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expiry;

import gordon.study.cache.ehcache3.basic.UserModel;
import gordon.study.cache.ehcache3.basic.UserModel.NullUser;

public class CustomExpiryCacheAsideUserService {

    private static final UserModel NULL_USER = new NullUser();

    private Ehcache<String, UserModel> cache;

    public CustomExpiryCacheAsideUserService() {
        cache = (Ehcache<String, UserModel>) UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, UserModel.class)
                .withExpiry(new CustomUserExpiry(new Duration(100, TimeUnit.SECONDS))).build(true);
    }

    public UserModel findUser(String id) {
        UserModel cached = cache.get(id);
        if (cached != null) {
            System.out.println("get user from cache: " + id);
            return cached;
        }
        UserModel user = null;
        if (!id.equals("0")) {
            user = new UserModel(id, "info ..."); // find user
        }
        System.out.println("get user from db: " + id);
        if (user == null) {
            user = NULL_USER;
        }
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

    private static class CustomUserExpiry implements Expiry<String, UserModel> {

        private final Duration ttl;

        public CustomUserExpiry(Duration ttl) {
            this.ttl = ttl;
        }

        @Override
        public Duration getExpiryForCreation(String key, UserModel value) {
            if (value.isNull()) {
                System.out.println("user is null: " + key);
                return new Duration(10, TimeUnit.SECONDS);
            }
            long length = ttl.getLength();
            if (length > 10) {
                long max = length / 5;
                long random = ThreadLocalRandom.current().nextLong(-max, max);
                return new Duration(ttl.getLength() + random, ttl.getTimeUnit());
            }
            return ttl;
        }

        @Override
        public Duration getExpiryForAccess(String key, ValueSupplier<? extends UserModel> value) {
            return null;
        }

        @Override
        public Duration getExpiryForUpdate(String key, ValueSupplier<? extends UserModel> oldValue, UserModel newValue) {
            return ttl;
        }
    }

    public static void main(String[] args) throws Exception {
        final CustomExpiryCacheAsideUserService service = new CustomExpiryCacheAsideUserService();
        for (int i = 0; i < 5; i++) {
            service.findUser("" + i);
        }
    }
}
