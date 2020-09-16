package gordon.study.mq.kafka.basic.helloworld;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class HelloProducer {
    private static Properties kafkaProps = new Properties();

    static {
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    public static void main(String[] args) {
        Producer<String, String> producer = new KafkaProducer<>(kafkaProps);
        ProducerRecord<String, String> record = new ProducerRecord<>("HelloTopic", "Chinese", "你好");
        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
