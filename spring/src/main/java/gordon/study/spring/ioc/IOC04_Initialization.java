package gordon.study.spring.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class IOC04_Initialization {

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("ioc/ioc04.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) factory);
        reader.loadBeanDefinitions(resource);
        factory.addBeanPostProcessor(new InnerBeanPostProcessor());
        InnerClass inner = factory.getBean("inner", InnerClass.class);
        System.out.println("bean name: " + inner.beanName);
        System.out.println("bean class loader: " + inner.classLoader);
        System.out.println("bean factory: " + inner.beanFactory);
        System.out.println("level: " + inner.level);
        factory.destroySingleton("inner");
    }

    static class InnerBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("in postProcessBeforeInitialization()...");
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("in postProcessAfterInitialization()...");
            if (bean instanceof InnerClass) {
                ((InnerClass) bean).level = 3;
            }
            return bean;
        }
    }

    static class InnerClass implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {

        private int level;

        private String beanName;

        private ClassLoader classLoader;

        private BeanFactory beanFactory;

        public InnerClass() {
            System.out.println("construct InnerClass...");
        }

        @Override
        public void setBeanName(String name) {
            this.beanName = name;
        }

        @Override
        public void setBeanClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        public void setLevel(int level) {
            System.out.println("in setLevel()...");
            this.level = level;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("in afterPropertiesSet()...");
            level = 2;
        }

        @Override
        public void destroy() throws Exception {
            System.out.println("in destroy()...");
        }

        public void init() {
            System.out.println("in init()...");
        }

        public void exit() {
            System.out.println("in exit()...");
        }

    }
}