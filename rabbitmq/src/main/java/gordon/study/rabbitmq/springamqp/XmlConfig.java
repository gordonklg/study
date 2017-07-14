package gordon.study.rabbitmq.springamqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfig {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq.xml");

        AmqpTemplate template = (AmqpTemplate) context.getBean("amqpTemplate");
        template.convertAndSend("spring", "foo");
        template.convertAndSend("spring", "bar");
        template.convertAndSend("spring", "tui");

        context.close();
    }
}
