package gordon.study.spring.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class SpringRetry04_ExponentialBackOffPolicy {

    public static void main(String[] args) throws Exception {
        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(new SimpleRetryPolicy(5));
        ExponentialBackOffPolicy ebop = new ExponentialBackOffPolicy();
        ebop.setInitialInterval(200);
        ebop.setMultiplier(4.0);
        ebop.setMaxInterval(2000);
        template.setBackOffPolicy(ebop);
        String result = template.execute(new RetryCallback<String, Exception>() {
            @Override
            public String doWithRetry(RetryContext context) throws Exception {
                long now = System.currentTimeMillis();
                Long timestamp = (Long) context.getAttribute("my.timestamp");
                if (timestamp != null) {
                    System.out.println("In method doWithRetry. Wait " + (now - timestamp));
                } else {
                    System.out.println("In method doWithRetry. First execution.");
                }
                context.setAttribute("my.timestamp", now);
                throw new NullPointerException("Runtime Exception");
            }
        });
        System.out.println("final result: " + result);
    }
}
