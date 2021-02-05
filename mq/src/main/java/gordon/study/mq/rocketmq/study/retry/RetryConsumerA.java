package gordon.study.mq.rocketmq.study.retry;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class RetryConsumerA {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TEST_COMSUMER_GROUP_01");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("TestTopic01", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%d %s Receive New Messages: %s %n", System.currentTimeMillis(), Thread.currentThread().getName(), msgs);
                for (MessageExt message : msgs) {
                    String key = message.getKeys();
                    if(key.equals("WRONG077")) {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        System.out.printf("RetryConsumerA Started.%n");
    }
}
