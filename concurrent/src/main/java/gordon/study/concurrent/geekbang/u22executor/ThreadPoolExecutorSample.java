package gordon.study.concurrent.geekbang.u22executor;

import java.util.concurrent.*;

public class ThreadPoolExecutorSample {

    /*
    WARNING: ThreadPoolExecutor.executor 方法是无锁实现，执行 addWorker 的线程并不一定真的会创建新线程，等等类似情况。
    因此，高并发场景下，并不会严格按照本 Sample 描述的规则运行！！
     */
    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS, queue);
        System.out.println(executor); // poolSize==0 // 没任务是不会自动创建新线程的
        executor.execute(new SleepTask(100)); // 第1个任务
        System.out.println(executor); // poolSize==1
        TimeUnit.MILLISECONDS.sleep(200); // 等待第1个任务执行结束，线程池中线程空闲

        executor.execute(new SleepTask(100)); // 第2批第1个任务
        System.out.println(executor); // poolSize==2 // 只要线程池的线程数没有达到core size (poolSize < corePoolSize)，无论是否有空闲线程，都会启动一个新线程处理新任务
        executor.execute(new SleepTask(100)); // 第2批第2个任务
        executor.execute(new SleepTask(100)); // 第2批第3个任务
        executor.execute(new SleepTask(100)); // 第2批第4个任务
        System.out.println(executor); // poolSize==2 // poolSize >= corePoolSize，且任务队列未满时，将新任务提交到任务队列排队
        executor.execute(new SleepTask(100)); // 第2批第5个任务
        System.out.println(executor); // poolSize==3 // poolSize >= corePoolSize，且任务队列满时，如果 poolSize < maximumPoolSize，启动新增线程来处理任务
        try {
            executor.execute(new SleepTask(100)); // 第2批第6个任务
        } catch (RejectedExecutionException e) {
            System.out.println(e.getMessage()); // 触发拒绝策略 RejectedExecutionHandler 定义的逻辑，默认为 AbortPolicy，抛Runtime异常
        }
        System.out.println(executor); // poolSize==3 // poolSize >= corePoolSize，且任务队列满时，如果 poolSize < maximumPoolSize，启动新增线程来处理任务
        TimeUnit.MILLISECONDS.sleep(200); // 等待第2批任务执行结束，线程池中线程空闲

        System.out.println(executor); // poolSize==3 // 额外线程还在存活时间内，未被回收
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println(executor); // poolSize==2 // 额外线程超时被回收

        executor.shutdown();
    }

    static class SleepTask implements Runnable {

        private long sleepTime;

        public SleepTask(long sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            } catch (RuntimeException e) {
            } catch (Throwable t) {
            }
            System.out.printf("%s worked for %d milliseconds.\n", Thread.currentThread().getName(), sleepTime);
        }
    }

}
