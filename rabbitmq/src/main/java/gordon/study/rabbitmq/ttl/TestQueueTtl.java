package gordon.study.rabbitmq.ttl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class TestQueueTtl {

    private static final String QUEUE_NAME = "queueTTL";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        final Channel senderChannel = connection.createChannel();
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-expires", 3000);
        senderChannel.queueDeclare(QUEUE_NAME, false, false, true, args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < Integer.MAX_VALUE;) {
                        String message = "NO. " + ++i;
                        TimeUnit.MILLISECONDS.sleep(100);
                        senderChannel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                    }
                    senderChannel.close();
                } catch (Exception e) {
                }
            }
        }).start();

        Channel consumerChannel = connection.createChannel();
        for (int i = 0; i < 5; i++) {
            GetResponse resp = consumerChannel.basicGet(QUEUE_NAME, true);
            if (resp == null) {
                System.out.println("Get Nothing!");
            } else {
                String message = new String(resp.getBody(), "UTF-8");
                System.out.printf("consume: %s\n", message);
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }

        System.out.println("stop get from queue for 4 seconds...");
        TimeUnit.MILLISECONDS.sleep(4000);
        consumerChannel.basicGet(QUEUE_NAME, true);
    }
}
