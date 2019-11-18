package gordon.study.concurrent.basic;

public class JoinSampleUseThreadObjectAsMonitor {

    public static void main(String[] args) {
        JoinSampleUseThreadObjectAsMonitor instance = new JoinSampleUseThreadObjectAsMonitor();
        instance.work();
    }

    private void work() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (Thread.currentThread()) {
                    System.out.println("try to notify main thread...");
                    Thread.currentThread().notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("End of working thread.");
            }

        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
        }
        System.out.println("End of main thread.");
    }
}

/*
想法很有趣，但是 join 中有 while(isAlive()) 循环，主线程被notify后依然继续wait。
 */