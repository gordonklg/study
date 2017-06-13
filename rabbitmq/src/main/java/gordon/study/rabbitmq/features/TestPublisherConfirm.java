package gordon.study.rabbitmq.features;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestPublisherConfirm {

    private static final String QUEUE_NAME = "publisherConfirm";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel senderChannel = connection.createChannel();

        senderChannel.queueDeclare(QUEUE_NAME, false, false, true, null);
        senderChannel.confirmSelect();
        senderChannel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.printf("nack: %s %s\n", deliveryTag, multiple);
            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.printf("ack: %s %s\n", deliveryTag, multiple);
            }
        });

        for (int i = 0; i < 10;) {
            String message = "NO. " + ++i;
            senderChannel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        }
        boolean isConfirm = senderChannel.waitForConfirms();
        System.out.println("isConfirm: " + isConfirm);
        connection.close();
    }
}
