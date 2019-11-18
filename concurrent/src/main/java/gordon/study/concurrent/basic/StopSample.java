package gordon.study.concurrent.basic;

public class StopSample {

    public static void main(String[] args) throws Exception {
        StopSample instance = new StopSample();
        instance.work();
    }

    @SuppressWarnings("deprecation")
    private void work() throws Exception {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                long sum = 0;
                for (int i = 0; i < Long.MAX_VALUE; i++) {
                    sum += i;
                }
                System.out.println("End of working thread." + sum);
            }

        });
        t.start();
        Thread.sleep(100);
        t.stop();
        Thread.sleep(100);
        System.out.println(t.isAlive());
    }
}
