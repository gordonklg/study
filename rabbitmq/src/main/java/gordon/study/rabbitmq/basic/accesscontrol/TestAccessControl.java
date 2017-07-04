package gordon.study.rabbitmq.basic.accesscontrol;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestAccessControl {

    private static final String QUEUE_NAME = "test";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test123");
        Connection connection = factory.newConnection();
        Channel senderChannel = connection.createChannel();
        // senderChannel.queueDeclare(QUEUE_NAME, true, false, false, null); // throw Exception
        senderChannel.queueDeclarePassive(QUEUE_NAME);
        senderChannel.basicPublish("", QUEUE_NAME, null, "test".getBytes("UTF-8"));
    }
}
