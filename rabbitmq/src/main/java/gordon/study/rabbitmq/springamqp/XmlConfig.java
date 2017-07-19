package gordon.study.rabbitmq.springamqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfig {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("springamqp1.xml");

        AmqpTemplate template = (AmqpTemplate) context.getBean("amqpTemplate");
        template.convertAndSend("spring", "foo");
        template.convertAndSend("spring", "bar");
        template.convertAndSend("spring", "tui");

        // context.close();
    }
}
