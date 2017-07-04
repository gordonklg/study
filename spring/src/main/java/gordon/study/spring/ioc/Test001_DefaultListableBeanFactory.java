package gordon.study.spring.ioc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import gordon.study.spring.common.Message;

public class Test001_DefaultListableBeanFactory {

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("ioc/test001.xml");
        BeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) factory);
        reader.loadBeanDefinitions(resource);
        Message message = factory.getBean("message", Message.class);
        System.out.println(message.getBody());
    }
}
