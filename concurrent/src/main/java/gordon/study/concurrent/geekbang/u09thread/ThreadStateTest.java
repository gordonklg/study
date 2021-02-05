package gordon.study.concurrent.geekbang.u09thread;

import java.util.Scanner;

public class ThreadStateTest {

    private static Object lock = new Object();

    public static void main(String[] args) {
        ThreadStateTest instance = new ThreadStateTest();
        Thread th1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                }
                long accu = 0;
                while (true) {
                    accu++;
                }
            }
        }, "Thread1");
        Thread th2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                }
                long accu = 0;
                while (true) {
                    accu++;
                }
            }
        }, "Thread2");
        Thread th3 = new Thread(() -> {
            synchronized (lock) {
                lock.notifyAll();
            }
        }, "Thread3");
        th1.start();
        th2.start();
        // 等待线程1、线程2启动完成并等待，jstack查看为 WAITING 状态
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        th3.start();
        // 再次jstack查看，线程1、线程2 一个是 BLOCKED，一个是 RUNNABLE
    }
}
