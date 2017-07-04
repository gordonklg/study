package gordon.study.rabbitmq.basic.fanout;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Fanout {

    private static final String EXCHANGE_NAME = "StatusUpdateFanout";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        final Channel senderChannel = connection.createChannel();
        senderChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10;) {
                        String message = "NO. " + ++i;
                        TimeUnit.MILLISECONDS.sleep(100);
                        senderChannel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                        System.out.printf("(%1$s)[===>%2$s    ] %3$s\n", "S", EXCHANGE_NAME + ":", message);
                        if (i == 4) {
                            latch.countDown();
                        }
                    }
                    senderChannel.close();
                } catch (Exception e) {
                }
            }
        }).start();

        final Channel consumerChannel1 = connection.createChannel();
        consumerChannel1.queueDeclare("SystemA", false, false, true, null);
        consumerChannel1.queueBind("SystemA", EXCHANGE_NAME, "");
        consumerChannel1.basicQos(3);
        Consumer consumer1 = new DefaultConsumer(consumerChannel1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf(" [    %2$s<===](%1$s) %3$s\n", "A", "SystemA", message);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                }
                consumerChannel1.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        consumerChannel1.basicConsume("SystemA", false, consumer1);

        latch.await();
        final Channel consumerChannel2 = connection.createChannel();
        consumerChannel2.queueDeclare("SystemB", false, false, true, null);
        consumerChannel2.queueBind("SystemB", EXCHANGE_NAME, "");
        consumerChannel2.basicQos(3);
        Consumer consumer2 = new DefaultConsumer(consumerChannel2) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf(" [    %2$s<===](%1$s) %3$s\n", "B -- won't receive first 4 messages", "SystemB", message);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                }
                consumerChannel2.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        consumerChannel2.basicConsume("SystemB", false, consumer2);
    }
}
