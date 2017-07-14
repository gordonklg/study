package gordon.study.rabbitmq.springamqp;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class CommonPrintBean {

    public void printMessage(Message message) {
        System.out.println("Message: " + message);
    }

    public void printMessage(Object message) {
        System.out.println("Object: " + message);
    }

    public void printMessage(String message) throws Exception {
        System.out.println("String: " + message);
    }
}
