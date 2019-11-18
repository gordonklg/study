package gordon.study.concurrent.basic;

public class JoinSample {

    public static void main(String[] args) {
        JoinSample instance = new JoinSample();
        instance.work();
    }

    private void work() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
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
