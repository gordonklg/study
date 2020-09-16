package gordon.study.mq.kafka.basic.helloworld;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by zhujiadong on 2018/4/18 16:27
 *
 * producer有一个buffer space pool来保存还没有被发送到broker的消息,后台I/O线程负责把这些消息转化成requests请求并发送到集群
 * 使用完producer后没有成功关闭它会导致内存泄漏
 *
 * producer处理过程:
 *   1. 创建ProducerRecord指定发送的topic和value,key和partition可选
 *   2. 序列化key和value
 *   3. 确定属于哪个partition,可以指定也可以自定义
 *   4. 把发送到相同的partition的records batch起来  单独的线程处理发送请求
 *   5. broker接收到消息,如果成功保存到磁盘返回RecordMetadata(topic, partition, offset),失败发送错误信息,当producer收到错误信息后会
 *      retry直到达到一定次数就认为发送失败
 *
 * producer的三种发送消息的方式:
 *   1. fire-and-forget
 *      不关心消息是否成功保存,大多数情况下会成功,因为kafka是HA的并且producer会retry,但有时会丢失消息
 *   2. synchronous send
 *      send()返回Future对象,可以调用get()方法来查看是否发送成功
 *   3. Asynchronous send
 *      给send方法传一个回调,当收到broker的response时触发回调
 *
 * 可以在多线程下使用producer来增加吞吐量
 *
 * KafkaProducer有两种类型的错误. Retriable可以通过重新发送message解决的错误,比如connection error可以等待重新连接在发送msg,no leader
 * error可以等待leader 被选举出来解决,KafkaProducer可以通过配置来自动重试来应对这些错误,仅当重试次数达到了抛出Retriable异常,有些错误是不
 * 可以通过retry解决的比如message size too large这种情况不会retry并且直接返回异常
 *
 * kafka的消息顺序保证(partition是有序的) 如果要保证顺序建议设置in.flight.request.per.session=1,如果发生retry,其他msg不能被发送
 */

public class KafkaProducerTest {
    private static Properties properties= new Properties();
    private static KafkaProducer<String, String> producer = null;
    static {
        ClassLoader classLoader = KafkaProducerTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("kafkaproducer.properties");
        try {
            properties.load(inputStream);
            producer = new KafkaProducer<String, String>(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这种方法会把消息放入buffer中,由单独的线程负责发送
     * send()返回Future对象但是这里没有处理,也就是不知道消息是否发送成功 这种方式在可容忍丢失消息的情况下可以使用,生产一般不用
     */
    private void unSaveProducer(){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("java_topic", "name","melo");
        try{
            producer.send(record);
        }catch (Exception e){
            e.printStackTrace();
            //尽管没有处理发送到kafka的异常但是别的异常也可能会发生比如SerializationException BufferExhaustedException TimeoutException InterruptException(发送线程被中断)
        }
    }

    /**
     * 同步发送
     */
    private void synchronousProducer(){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("java_topic", "name", "Rilley");
        try{
            Future<RecordMetadata> future = producer.send(record);
            future.get(); //wait for a reply from kafka,如果没有成功抛出异常,成功返回元信息
            System.out.println(future.get());
            RecordMetadata recordMetadata = future.get();
            System.out.println(recordMetadata.offset());
        }catch (Exception e){
            e.printStackTrace(); //其它异常
        }
    }

    /**
     * 假设网络往返(app--kafka)一次需要10ms,那么如果等待每条消息的reply,发送100条消息要1s.如果不需要reply那么效率会更高,大多数情况并不需要
     * reply
     * 如果既要异步也要处理错误,可以使用回调
     * 异步发送要flush()
     */
    private void asynchronousProducer(){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("java_topic", "name", "naomi");
        producer.send(record, new ProducerCallback());
        producer.flush();
    }

    public static void main(String[] args) {
        KafkaProducerTest kafkaProducerTest = new KafkaProducerTest();
//        kafkaProducerTest.unSaveProducer();
//        kafkaProducerTest.synchronousProducer();
        kafkaProducerTest.asynchronousProducer();
    }
}