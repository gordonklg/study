package gordon.study.rocketmq.study.rebalancer;

import org.apache.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class AllMessageQueueStrategy implements AllocateMessageQueueStrategy {

    @Override
    public List<MessageQueue> allocate(String consumerGroup, String currentCID, List<MessageQueue> mqAll, List<String> cidAll) {
        System.out.printf("allocate. consumerGroup %s, currentCID %s, cidAll %s.\n", consumerGroup, currentCID, cidAll.toString());
        return mqAll;
    }

    @Override
    public String getName() {
        return "ALL";
    }
}