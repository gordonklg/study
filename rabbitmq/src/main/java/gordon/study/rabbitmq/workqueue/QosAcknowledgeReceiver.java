package gordon.study.rabbitmq.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class QosAcknowledgeReceiver {

    private static final String QUEUE_NAME = "tasks";

    private String name;
    
    private int sleepTime;

    public QosAcknowledgeReceiver(String name, int sleepTime) {
        this.name = name;
        this.sleepTime = sleepTime;
    }

    public void work() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf(" [    %2$s<===](%1$s) %3$s\n", name, QUEUE_NAME, message);
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
