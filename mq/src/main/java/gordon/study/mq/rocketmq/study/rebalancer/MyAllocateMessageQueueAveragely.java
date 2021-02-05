package gordon.study.mq.rocketmq.study.rebalancer;

import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class MyAllocateMessageQueueAveragely extends AllocateMessageQueueAveragely {

    @Override
    public List<MessageQueue> allocate(String consumerGroup, String currentCID, List<MessageQueue> mqAll, List<String> cidAll) {
        System.out.printf("allocate. consumerGroup %s, currentCID %s, cidAll %s.\n", consumerGroup, currentCID, cidAll.toString());
        List<MessageQueue> result = super.allocate(consumerGroup, currentCID, mqAll, cidAll);
        result.stream().forEach((e) -> {
            System.out.print(e.getQueueId() + " ");
        });
        System.out.println("\n");
        return result;
    }

    @Override
    public String getName() {
        return "MY_AVG";
    }
}