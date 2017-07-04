package gordon.study.spring.ioc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import gordon.study.spring.common.Employee;

public class Test003_CircularReference {

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("ioc/test003.xml");
        BeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) factory);
        reader.loadBeanDefinitions(resource);
        Employee chairman = factory.getBean("chairman", Employee.class);
        System.out.println(chairman.getCompany().getName());
    }
}
