package gordon.study.spring.ioc;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import gordon.study.spring.common.Message;

public class TestDefaultListableBeanFactory {

    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("ioc/test01.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        Message message = factory.getBean("message", Message.class);
        System.out.println(message.getBody());
    }
}
