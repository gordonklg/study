package gordon.study.rabbitmq.basic.helloworld2;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NewExchangeSender {

    private static final String EXCHANGE_NAME = "p2p";

    private String queueName;

    private String routingKey;

    private boolean declare;

    public NewExchangeSender(String queueName, String routingKey, boolean declare) {
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.declare = declare;
    }

    public void work() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        if (declare) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }

        for (int i = 0; i < 5;) {
            String message = "NO. " + ++i;
            TimeUnit.MILLISECONDS.sleep(1000);
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.printf("(%1$s)[===>%2$s    ] %3$s\n", "NESender", EXCHANGE_NAME + ":" + queueName, message);
        }

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, "fin".getBytes("UTF-8"));

        channel.close();
        connection.close();
    }
}
