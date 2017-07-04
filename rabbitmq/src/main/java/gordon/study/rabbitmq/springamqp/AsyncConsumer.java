package gordon.study.rabbitmq.springamqp;

import java.net.URI;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("amqp://guest:guest@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setQueueNames("spring");
        container.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.println("received: " + message);
            }
        });
        container.start();

        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("spring", "foo");
        template.convertAndSend("spring", "bar");
        template.convertAndSend("spring", "tui");
    }
}
