package gordon.study.concurrent.basic;

public class StopFlagSample {

    public static void main(String[] args) throws Exception {
        StopFlagSample instance = new StopFlagSample();
        instance.work();
    }

    private void work() throws Exception {
        SumThread t = new SumThread();
        t.start();
        Thread.sleep(100);
        t.stopIt();
        Thread.sleep(100);
        System.out.println(t.isAlive());
    }

    private class SumThread extends Thread {

        private volatile boolean stop = false;

        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < Long.MAX_VALUE; i++) {
                if (stop) {
                    break;
                }
                sum += i;
            }
            System.out.println("End of working thread. " + (stop ? "stop" : sum));
        }

        public void stopIt() {
            stop = true;
        }
    }
}
