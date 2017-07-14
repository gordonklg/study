package gordon.study.springboot.basic;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

    public void sayHello() {
        System.out.println("============Hello World!");
    }
    
    public void sayAdvisorBefore(String param) {  
        System.out.println("============say " + param);  
    }  
    
    public boolean check() {
//        return false;
        throw new RuntimeException("excp");
    }
}
