package gordon.study.spring.retry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class SpringRetry03_R {

    public static void main(String[] args) throws Exception {
        RetryTemplate template = new RetryTemplate();
        Map<Class<? extends Throwable>, Boolean> map = new ConcurrentHashMap<>();
        map.put(Exception.class, Boolean.TRUE);
        map.put(RuntimeException.class, Boolean.FALSE);
        SimpleRetryPolicy srp = new SimpleRetryPolicy(3, map);
        template.setRetryPolicy(srp);
        String result = template.execute(new RetryCallback<String, Exception>() {
            public String doWithRetry(RetryContext context) throws Exception {
                try {
                    return String.format("%d", 6 / 0);
                } catch (Exception e) {
                    System.out.println(e);
                    throw e;
                }
            }
        });
        System.out.println("final result: " + result);
    }
}
