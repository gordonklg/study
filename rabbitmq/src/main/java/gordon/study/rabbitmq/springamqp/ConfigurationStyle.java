package gordon.study.rabbitmq.springamqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationStyle {

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory());
        container.setQueueNames("spring");
        container.setMessageListener(messageListenerAdapter());
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        MessageListenerAdapter adapter = new MessageListenerAdapter(commonPrintBean());
        adapter.setDefaultListenerMethod("printMessage");
        return adapter;
    }

    @Bean
    public CommonPrintBean commonPrintBean() {
        return new CommonPrintBean();
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        AmqpTemplate tempalte = new RabbitTemplate(rabbitConnectionFactory());
        return tempalte;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConfigurationStyle.class);
        context.refresh();

        AmqpTemplate template = (AmqpTemplate) context.getBean("amqpTemplate");
        template.convertAndSend("spring", "foo");
        template.convertAndSend("spring", "bar");
        template.convertAndSend("spring", "tui");

        // context.close();
    }
}
