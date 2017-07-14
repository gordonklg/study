package gordon.study.rabbitmq.springamqp;

import java.net.URI;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.rabbitmq.client.Channel;

public class AsyncConsumerManualAck {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("amqp://guest:guest@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueueNames("spring");
        container.setMessageConverter(new ScornMessageConverter());
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                System.out.println("Processed " + new String(message.getBody()));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        container.start();

        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("spring", "1");
        template.convertAndSend("spring", "2");
        template.convertAndSend("spring", "3");
        template.convertAndSend("spring", "4");
    }

    private static class ScornMessageConverter implements MessageConverter {
        @Override
        public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
            System.out.println("toMessage? you'll never see it!");
            return null;
        }

        @Override
        public Object fromMessage(Message message) throws MessageConversionException {
            System.out.println("fromMessage? you'll never see it!");
            return null;
        }
    }
}
