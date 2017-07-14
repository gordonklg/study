package gordon.study.rabbitmq.springamqp;

import java.net.URI;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class AsyncConsumerWithAdapter {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("amqp://guest:guest@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        MessageListenerAdapter adapter = new MessageListenerAdapter(new CommonPrintBean());
        adapter.addQueueOrTagToMethodName("spring", "printMessage");
        // adapter.setMessageConverter(null);
        container.setQueueNames("spring");
        container.setMessageListener(adapter);
        container.start();

        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("spring", "foo");
        template.convertAndSend("spring", "bar");
        template.convertAndSend("spring", "tui");
        template.convertAndSend("spring", "tdd");
    }

    public static class CommonPrintBean {
        public void printMessage(Message message) {
            System.out.println("Message: " + message);
        }

        public void printMessage(Object message) {
            System.out.println("Object: " + message);
        }

        public void printMessage(String message) throws Exception {
            if ("tui".equals(message)) {
                throw new AmqpRejectAndDontRequeueException("tui?");
            }
            System.out.println("String: " + message);
        }
    }
}
