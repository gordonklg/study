package gordon.study.concurrent.geekbang.u14lockcondition.simulatedubbo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MockServer {

    private AtomicLong accu = new AtomicLong(0);

    private ExecutorService es = Executors.newFixedThreadPool(10);

    public void request(DefaultFuture future) {
        es.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            future.doReceived("Hello World!" + accu.incrementAndGet());
        });
    }
}
