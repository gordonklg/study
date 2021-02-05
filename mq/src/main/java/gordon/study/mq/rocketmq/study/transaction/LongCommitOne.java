package gordon.study.mq.rocketmq.study.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;

public class LongCommitOne {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        TransactionMQProducer producer = new TransactionMQProducer("TEST_PRODUCER_GROUP_01");
        producer.setNamesrvAddr("localhost:9876");
        producer.setExecutorService(Executors.newSingleThreadExecutor());
        producer.setTransactionListener(new TransactionListener() {

            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("local trans for: " + msg);
                try {
                    Thread.sleep(1000 * 120); // 2 minutes
                } catch (InterruptedException e) {
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("check local trans state: " + msg);
                return LocalTransactionState.UNKNOW;
            }
        });
        producer.start();

        try {
            Message msg =
                    new Message("TestTopicTrans", "tagA", "KEY202",
                            ("Hello RocketMQ 202").getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", sendResult);
        } catch (MQClientException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}