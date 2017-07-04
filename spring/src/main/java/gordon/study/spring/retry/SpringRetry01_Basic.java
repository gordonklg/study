package gordon.study.spring.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

public class SpringRetry01_Basic {

    public static void main(String[] args) throws Exception {
        RetryTemplate template = new RetryTemplate();
        String result = template.execute(new RetryCallback<String, Exception>() {
            @Override
            public String doWithRetry(RetryContext context) throws Exception {
                System.out.println("in method doWithRetry");
                throw new NullPointerException("Runtime Exception");
            }
        });
        System.out.println("final result: " + result);
    }
}
