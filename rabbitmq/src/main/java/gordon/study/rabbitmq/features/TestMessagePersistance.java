package gordon.study.rabbitmq.features;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class TestMessagePersistance {

    private static final String QUEUE_NAME = "persistance";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel senderChannel = connection.createChannel();
        senderChannel.queueDeclare(QUEUE_NAME, true, false, false, null);

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().deliveryMode(2).build();
        senderChannel.basicPublish("", QUEUE_NAME, properties, "Message 1".getBytes("UTF-8"));

        senderChannel.basicPublish("", QUEUE_NAME, MessageProperties.MINIMAL_PERSISTENT_BASIC, "Message 2".getBytes("UTF-8"));
    }
}
