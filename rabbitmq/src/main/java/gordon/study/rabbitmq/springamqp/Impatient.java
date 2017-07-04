package gordon.study.rabbitmq.springamqp;

import java.net.URI;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class Impatient {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("amqp://guest:guest@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue("spring"));
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("spring", "foo");
        String foo = (String) template.receiveAndConvert("spring");
        System.out.println(foo);
    }
}
