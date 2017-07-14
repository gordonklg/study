package gordon.study.redis.springdata.basic;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisShardInfo;

public class HelloWorld_Clean {

    public static void main(String[] args) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(new JedisShardInfo("10.203.25.128", 6379));
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
        template.opsForValue().set("test002", "Hello World!");
        String s = template.opsForValue().get("test002");
        System.out.println(s);
    }
}
