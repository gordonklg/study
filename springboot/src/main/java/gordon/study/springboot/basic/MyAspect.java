package gordon.study.springboot.basic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(-1000)
public class MyAspect {

    //    @Pointcut(value="execution(* gordon.study.springboot..*.sayAdvisorBefore(..)) && args(param)", argNames = "param")  
    //    public void beforePointcut(String param) {}  
    //    
    //    @Before(value = "beforePointcut(param)", argNames = "param")  
    //    public void beforeAdvice(String param) {  
    //        System.out.println("===========before advice param:" + param);  
    //    }  

    @Pointcut(value = "execution(* gordon.study.springboot..*.*(..))")
    public void beforePointcut2() {
    }

    @Around(value = "beforePointcut2()")
    public Object beforeAdvice2(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========before advice2 param:");
        try {
            pjp.proceed();
        } catch (Exception e) {
            return false;
        }
        System.out.println("===========after advice2 param:");
        return true;
    }

//    @AfterThrowing(value = "beforePointcut2()", argNames = "exception", throwing = "exception")
//    public void afterThrowingAdvice(Exception exception) {
//        System.out.println("===========after throwing advice exception:" + exception);
//    }

    //前置通知  
    public void beforeAdvice() {
        System.out.println("===========before advice");
    }

    //后置最终通知  
    public void afterFinallyAdvice() {
        System.out.println("===========after finally advice");
    }
}