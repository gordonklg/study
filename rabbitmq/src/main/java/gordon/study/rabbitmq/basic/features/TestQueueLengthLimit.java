package gordon.study.rabbitmq.basic.features;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class TestQueueLengthLimit {

    private static final String QUEUE_NAME = "queueLengthLimit";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel senderChannel = connection.createChannel();
        Channel consumerChannel = connection.createChannel();

        // 设置队列最大消息数量为5
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-max-length", 5);
        senderChannel.queueDeclare(QUEUE_NAME, false, false, true, args);
        // 发布6个消息
        for (int i = 0; i < 6;) {
            String message = "NO. " + ++i;
            senderChannel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        }

        // 获取的消息为 NO. 2，说明队列头部第一条消息被抛弃
        Thread.sleep(100);
        GetResponse resp = consumerChannel.basicGet(QUEUE_NAME, false);
        String message = new String(resp.getBody(), "UTF-8");
        System.out.printf("consume: %s\n", message);
        System.out.printf("queue size: %d\n", resp.getMessageCount());

        // 现在队列中有4个 Ready消息，1个 Unacked消息。此时再发布两条消息，应该只有 NO. 3 被抛弃。
        senderChannel.basicPublish("", QUEUE_NAME, null, "NO. 7".getBytes("UTF-8"));
        senderChannel.basicPublish("", QUEUE_NAME, null, "NO. 8".getBytes("UTF-8"));
        Thread.sleep(100);
        GetResponse resp2 = consumerChannel.basicGet(QUEUE_NAME, false);
        message = new String(resp2.getBody(), "UTF-8");
        System.out.printf("consume: %s\n\n", message);

        // 现在队列中有4个 Ready消息，2个 Unacked消息。
        // 此时Nack，消息2、4取消退回队列头导致队列消息数量超过设定值，谁能留下？
        consumerChannel.basicNack(resp2.getEnvelope().getDeliveryTag(), true, true);
        Thread.sleep(100);
        while (true) {
            resp = consumerChannel.basicGet(QUEUE_NAME, true);
            if (resp == null) {
                break;
            } else {
                message = new String(resp.getBody(), "UTF-8");
                System.out.printf("consume: %s\n", message);
            }
        }
    }
}
