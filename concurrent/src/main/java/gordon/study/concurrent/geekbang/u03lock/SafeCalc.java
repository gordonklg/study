package gordon.study.concurrent.geekbang.u03lock;

public class SafeCalc {

    private long count = 0;

    private synchronized long get() {
        return count;
    }

    private synchronized void addOne() {
        count += 1;
    }

    private void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            addOne();
        }
    }

    public void calc() {
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(() -> add10K());
        Thread th2 = new Thread(() -> add10K());
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        SafeCalc instance = new SafeCalc();
        instance.calc();
        System.out.println(instance.get());
    }
}
