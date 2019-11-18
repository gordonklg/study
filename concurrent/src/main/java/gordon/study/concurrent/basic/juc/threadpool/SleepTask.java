package gordon.study.concurrent.basic.juc.threadpool;

public class SleepTask implements Runnable {

    private int num;

    private long sleepTime;

    private long timestamp = System.currentTimeMillis();

    public SleepTask(int num, long sleepTime) {
        this.num = num;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        System.out.println(num + " in thread: " + Thread.currentThread().getName() + " wait " + (System.currentTimeMillis() - timestamp) / 1000.0);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
