package gordon.study.concurrent.basic;

public class NotifiedThreadStateTest {

    private Object lock = new Object();

    public static void main(String[] args) throws Exception {
        NotifiedThreadStateTest instance = new NotifiedThreadStateTest();
        instance.work();
    }

    private void work() {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + " : Thread 1 start.");
                synchronized (lock) {
                    System.out.println(System.currentTimeMillis() + " : Thread 1 wait for lock.");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(System.currentTimeMillis() + " : Thread 1 end.");
            }
        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + " : Thread 2 start.");
                synchronized (lock) {
                    System.out.println(System.currentTimeMillis() + " : Thread 2 notify one thread.");
                    lock.notify();
                    System.out.println(System.currentTimeMillis() + " : Thread 2 end.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.out.println(t1.getState());
    }
}
