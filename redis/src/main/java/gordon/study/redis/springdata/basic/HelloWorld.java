package gordon.study.redis.springdata.basic;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class HelloWorld {

    public static void main(String[] args) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName("10.203.25.128");
        connectionFactory.setUsePool(false);
        connectionFactory.afterPropertiesSet();
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        template.opsForValue().set("test001", "Hello World!");
        String s = template.opsForValue().get("test001");
        System.out.println(s);
    }
}
