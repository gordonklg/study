package gordon.study.concurrent.basic.juc.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest01 {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutorTest01 instance = new ThreadPoolExecutorTest01();
        instance.work();
    }

    private void work() throws Exception {
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(8));
        System.out.println(es.getPoolSize());

        es.execute(new SleepTask(1, 100)); // add a new worker thread pool-1-thread-1
        System.out.println(es.getPoolSize());

        Thread.sleep(200); // worker finished the task
        es.execute(new SleepTask(2, 1000)); // add a new worker thread pool-1-thread-2
        System.out.println(es.getPoolSize());

        Thread.sleep(100);
        es.execute(new SleepTask(3, 1000)); // reach corePoolSize, reuse pool-1-thread-1
        System.out.println(es.getPoolSize());

        Thread.sleep(100);
        es.submit(new SleepTask(4, 1000)); // wait in queue

        es.shutdown();
    }

}