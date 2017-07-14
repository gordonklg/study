package gordon.study.springboot.basic;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCacheTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");

        HelloWorldService hello = (HelloWorldService)context.getBean("helloWorldService");
        System.out.println("---------------" +  hello.check());
//        AmqpTemplate template = (AmqpTemplate)context.getBean("amqpTemplate");
//        System.out.println(template);
//        template.convertAndSend("spring", "foo");
//        String foo = (String) template.receiveAndConvert("spring");
//        System.out.println(foo);
    
//    context.close();
    }
}
