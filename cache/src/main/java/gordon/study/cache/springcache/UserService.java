package gordon.study.cache.springcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import gordon.study.cache.ehcache3.basic.UserModel;

@Service
@CacheConfig(cacheNames = "userCache")
public class UserService {

    @Autowired
    UserRepository repo;

    @Cacheable
    public UserModel findUser(String id) {
        return repo.findUser(id);
    }

    @CachePut(key="#id")
    public UserModel updateUser(String id, String info) {
        return repo.updateUser(id, info);
    }

    @CacheEvict
    public UserModel deleteUser(String id) {
        return repo.deleteUser(id);
    }
}
