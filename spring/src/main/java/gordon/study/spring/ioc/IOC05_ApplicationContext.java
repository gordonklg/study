package gordon.study.spring.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import gordon.study.spring.common.Employee;

public class IOC05_ApplicationContext {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ioc/ioc03.xml")) {
            Employee chairman = ctx.getBean("chairman", Employee.class);
            System.out.println(chairman.getCompany().getName());
        }
    }
}
