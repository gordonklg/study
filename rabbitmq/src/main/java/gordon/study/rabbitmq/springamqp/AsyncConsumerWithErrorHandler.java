package gordon.study.rabbitmq.springamqp;

import java.net.URI;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler.DefaultExceptionStrategy;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class AsyncConsumerWithErrorHandler {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("amqp://guest:guest@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setPrefetchCount(3);
        container.setQueueNames("spring");
        container.setMessageListener(new MessageListener() {
            private int count = 0;
            public void onMessage(Message message) {
                count++;
                if (count == 1) {
                    System.out.println("Bad luck for " + new String(message.getBody()));
                    throw new RuntimeException();
                } else if (count == 5) {
                    System.out.println("Real bad luck for " + new String(message.getBody()));
                    throw new UserDefineException();
                    // throw new AmqpRejectAndDontRequeueException("wrong");
                }
                System.out.println("Processed " + new String(message.getBody()));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });
        container.setErrorHandler(new ConditionalRejectingErrorHandler(new DefaultExceptionStrategy() {
            protected boolean isUserCauseFatal(Throwable cause) {
                if (cause instanceof UserDefineException) {
                    return true;
                }
                return false;
            }
        }));
        container.start();

        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("spring", "1");
        template.convertAndSend("spring", "2");
        template.convertAndSend("spring", "3");
        template.convertAndSend("spring", "4");
        template.convertAndSend("spring", "5");
    }

    @SuppressWarnings("serial")
    private static class UserDefineException extends RuntimeException {
    }
}
