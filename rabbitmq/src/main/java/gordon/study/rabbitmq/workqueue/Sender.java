package gordon.study.rabbitmq.workqueue;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

    private static final String QUEUE_NAME = "tasks";

    private String name;

    public Sender(String name) {
        this.name = name;
    }

    public void work() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 10;) {
            String message = "NO. " + ++i;
            TimeUnit.MILLISECONDS.sleep(100);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.printf("(%1$s)[===>%2$s    ] %3$s\n", name, ":" + QUEUE_NAME, message);
        }

        channel.close();
        connection.close();
    }
}
