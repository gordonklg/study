package gordon.study.concurrent.geekbang.u23future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskSample {

    public static void main(String[] args) throws Exception {
        // 创建FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 提交FutureTask
        es.submit(futureTask);
        // 获取计算结果
        Integer result = futureTask.get();
        System.out.println(result);
        es.shutdown();

        // 直接用线程执行 FutureTask
        Thread t = new Thread(futureTask);
        t.start();
        System.out.println(futureTask.get());
    }
}
