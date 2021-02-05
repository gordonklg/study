package gordon.study.mq.rocketmq.study.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class ScheduledMessageProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("TEST_PRODUCER_GROUP_01");
        producer.setNamesrvAddr("localhost:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 1;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic01", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(10);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

}