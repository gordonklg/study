package gordon.study.rabbitmq.helloworld2;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class NewExchangeReceiver {

    private static final String EXCHANGE_NAME = "p2p";

    private String queueName;

    private String routingKey;

    private boolean declare;

    public NewExchangeReceiver(String queueName, String routingKey, boolean declare) {
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.declare = declare;
    }

    public void work() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        if (declare) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                if ("fin".equals(message)) {
                    connection.close();
                    return;
                }
                System.out.printf(" [    %2$s<===](%1$s) %3$s\n", "NEReceiver", queueName, message);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
