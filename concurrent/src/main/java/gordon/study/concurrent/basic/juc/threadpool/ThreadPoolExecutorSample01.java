package gordon.study.concurrent.basic.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorSample01 {

    public static void main(String[] args) {
        ThreadPoolExecutorSample01 instance = new ThreadPoolExecutorSample01();
        instance.work();
    }

    private void work() {
        ExecutorService es = new ThreadPoolExecutor(
                3, // 线程池中至少有3个线程
                5, // 最多有5个线程
                60, // 超过60秒的空闲线程会被回收
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), // 等待队列
                Executors.defaultThreadFactory(), // 线程工厂
                new ThreadPoolExecutor.DiscardPolicy()); // 抛弃掉无能力处理的线程
        for (int i = 0; i < 30; i++) {
            es.execute(new SleepTask(i, 1000));
        }
        es.shutdown();
    }

}
/*
0 in thread: pool-1-thread-1
1 in thread: pool-1-thread-2
2 in thread: pool-1-thread-3 // 初始有3个工作线程。task 3-12 进等待队列排队
13 in thread: pool-1-thread-4 // 要等队列满才会开新线程！！！
14 in thread: pool-1-thread-5 // 开新线程直到达到maxPoolSize
----------1s---------------
3 in thread: pool-1-thread-5 // 顺序从队列中取出task执行
4 in thread: pool-1-thread-4
5 in thread: pool-1-thread-3
6 in thread: pool-1-thread-2
7 in thread: pool-1-thread-1
----------1s---------------
8 in thread: pool-1-thread-5 // 顺序从队列中取出task执行
9 in thread: pool-1-thread-4
10 in thread: pool-1-thread-1
11 in thread: pool-1-thread-3
12 in thread: pool-1-thread-2
----------1s---------------  // task 15-99 被抛弃
 */