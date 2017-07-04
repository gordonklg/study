package gordon.study.rabbitmq.springamqp;

import java.net.URI;
import java.util.Date;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

public class JsonMessage {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("amqp://guest:guest@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        MessageConverter mc = new Jackson2JsonMessageConverter();
        template.setMessageConverter(mc);
        Student gordon = new Student();
        gordon.setName("Gordon");
        gordon.setBirthday(new Date());
        gordon.setTall(172);
        template.convertAndSend("spring", gordon);
        Student student = (Student) template.receiveAndConvert("spring");
        System.out.println(student.getBirthday());

        template.convertAndSend("spring", gordon);
        template.setMessageConverter(new SimpleMessageConverter());
        byte[] message = (byte[]) template.receiveAndConvert("spring");
        System.out.println(new String(message));

        template.convertAndSend("spring", gordon); // SimpleMessageConverter无法处理非可序列化对象，实际消息体是null
        Object obj = template.receiveAndConvert("spring");
        System.out.println(obj.getClass());
        System.out.println(new String((byte[])obj));
    }
}
