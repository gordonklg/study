package gordon.study.cache.springcache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import gordon.study.cache.ehcache3.basic.UserModel;

@Repository
public class UserRepository {

    private static final Map<String, UserModel> users = new ConcurrentHashMap<>();

    static {
        for (int i = 1; i <= 7; i++) {
            users.put("id" + i, new UserModel("id" + i, "phone" + i, "email" + i, "openid" + i, "info" + i));
        }
    }

    public UserModel findUser(String id) {
        System.out.println("query user in database by id: " + id);
        return users.get(id);
    }

    public UserModel updateUser(String id, String info) {
        System.out.println("update user in database by id: " + id);
        UserModel user = users.get(id);
        if (user != null) {
            user.setInfo(info);
        }
        return user;
    }

    public UserModel deleteUser(String id) {
        System.out.println("delete user in database by id: " + id);
        return users.remove(id);
    }

}
