package gordon.study.rabbitmq.dlx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

public class TestDlx {

    private static final String DLX_EXCHANGE_NAME = "exchangeDLX";

    private static final String DLX_QUEUE_NAME = "queueDLX";

    private static final String QUEUE_NAME = "queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel senderChannel = connection.createChannel();
        Channel consumerChannel = connection.createChannel();

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 3000); // 设置队列中消息存活时间为3秒
        args.put("x-max-length", 5); // 设置队列最大消息数量为5
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME); // 设置DLX
        senderChannel.queueDeclare(QUEUE_NAME, false, false, true, args);

        senderChannel.queueDeclare(DLX_QUEUE_NAME, false, false, true, null);
        senderChannel.exchangeDeclare(DLX_EXCHANGE_NAME, "direct", false, true, null);
        // 将死信队列绑定到死信交换机上，绑定键为 QUEUE_NAME。消息发送时使用的绑定键也会是 QUEUE_NAME
        senderChannel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, QUEUE_NAME);

        // 发布6个消息
        for (int i = 0; i < 6;) {
            String message = "NO. " + ++i;
            senderChannel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        }

        // 监视死信队列
        Consumer dlxConsumer = new DefaultConsumer(consumerChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("consume: %s, envelop: %s, properties: %s\n", message, envelope, properties);
            }
        };
        consumerChannel.basicConsume(DLX_QUEUE_NAME, true, dlxConsumer);

        Thread.sleep(100);
        GetResponse resp = consumerChannel.basicGet(QUEUE_NAME, false);
        consumerChannel.basicReject(resp.getEnvelope().getDeliveryTag(), false);
    }
}
