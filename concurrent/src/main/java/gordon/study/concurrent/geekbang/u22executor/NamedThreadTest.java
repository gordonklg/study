package gordon.study.concurrent.geekbang.u22executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class NamedThreadTest {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.SECONDS, queue,
                new ThreadFactory() {

                    private AtomicLong index = new AtomicLong();

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "BUSINESS-A-" + index.incrementAndGet());
                        return thread;
                    }
                });

        for (int i = 0; i < 20; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName()));
        }

        executor.shutdown();
    }


}
