package gordon.study.spring.retry;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class SpringRetry02_Recovery {

    public static void main(String[] args) throws Exception {
        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(new SimpleRetryPolicy(2));
        template.setBackOffPolicy(new FixedBackOffPolicy());
        template.registerListener(new RetryListener() {
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                System.out.println("open");
                return true;
            }
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                System.out.println("close");
            }
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                System.out.println("onError");
            }
        });
        String result = template.execute(new RetryCallback<String, Exception>() {
            public String doWithRetry(RetryContext context) throws Exception {
                System.out.println("in method doWithRetry");
                throw new NullPointerException("Runtime Exception");
            }
        }, new RecoveryCallback<String>() {
            public String recover(RetryContext context) throws Exception {
                return "recover";
            }
        });
        System.out.println("final result: " + result);
    }
}
