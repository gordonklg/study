package gordon.study.cache.springcache.listcache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import gordon.study.cache.ehcache3.basic.UserModel;

@Service
@CacheConfig(cacheNames = "userCache")
public class ListCacheUserService {

    @Autowired
    ListCacheUserRepository repo;

    @Caching(cacheable = { @Cacheable(key = "'all'") })
    public List<UserModel> allUsers() {
        return repo.allUsers();
    }

    @Caching(cacheable = { @Cacheable(key = "'i'+#id") }, put = {
            @CachePut(key = "'p'+#result.phone", condition = "#root.caches[0].get('i'+#id) == null and #result != null and #result.phone != null"),
            @CachePut(key = "'e'+#result.email", condition = "#root.caches[0].get('i'+#id) == null and #result != null and #result.email != null") })
    public UserModel findUser(String id) {
        return repo.findUser(id);
    }

    @Caching(cacheable = { @Cacheable(key = "'p'+#phone") }, put = {
            @CachePut(key = "'i'+#result.id", condition = "#root.caches[0].get('p'+#phone) == null and #result != null"),
            @CachePut(key = "'e'+#result.email", condition = "#root.caches[0].get('p'+#phone) == null and #result != null and #result.email != null") })
    public UserModel findUserByPhone(String phone) {
        return repo.findUserByPhone(phone);
    }

    @Caching(cacheable = { @Cacheable(key = "'e'+#email") }, put = {
            @CachePut(key = "'i'+#result.id", condition = "#root.caches[0].get('e'+#email) == null and #result != null"),
            @CachePut(key = "'p'+#result.phone", condition = "#root.caches[0].get('e'+#email) == null and #result != null and #result.phone != null") })
    public UserModel findUserByEmail(String email) {
        return repo.findUserByEmail(email);
    }

    @Caching(put = { @CachePut(key = "'i'+#id", condition = "#result != null"),
            @CachePut(key = "'p'+#result.phone", condition = "#result != null and #result.phone != null"),
            @CachePut(key = "'e'+#result.email", condition = "#result != null and #result.email != null") })
    public UserModel updateUser(String id, String info) {
        return repo.updateUser(id, info);
    }

    @Caching(evict = { @CacheEvict(key = "'i'+#id"),
            @CacheEvict(key = "'p'+#result.phone", condition = "#root.caches[0].get('i'+#id) != null"),
            @CacheEvict(key = "'e'+#result.email", condition = "#root.caches[0].get('i'+#id) != null") })
    public UserModel deleteUser(String id) {
        return repo.deleteUser(id);
    }
}
