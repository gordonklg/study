package gordon.study.rabbitmq.basic.ttl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestPerQueueMsgTtl {

    private static final String QUEUE_NAME = "perQueueMsgTTL";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel consumerChannel = connection.createChannel();
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 5000);
        consumerChannel.queueDeclare(QUEUE_NAME, false, false, true, args);
        consumerChannel.basicQos(3);
        Consumer consumer1 = new DefaultConsumer(consumerChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("consume: %s\n", message);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                }
                consumerChannel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        consumerChannel.basicConsume(QUEUE_NAME, false, consumer1);

        Channel senderChannel = connection.createChannel();
        for (int i = 0; i < 10;) {
            String message = "NO. " + ++i;
            TimeUnit.MILLISECONDS.sleep(100);
            senderChannel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        }
        senderChannel.close();
    }
}
