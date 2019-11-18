package gordon.study.concurrent.basic;

public class InterruptSample {

    public static void main(String[] args) throws Exception {
        InterruptSample instance = new InterruptSample();
        instance.work();
    }

    private void work() throws Exception {
        SumThread t = new SumThread();
        t.start();
        Thread.sleep(100);
        t.interrupt();
        Thread.sleep(100);
        System.out.println(t.isAlive());
    }

    private class SumThread extends Thread {

        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < Long.MAX_VALUE; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                sum += i;
            }
            System.out.println("End of working thread. " + (Thread.currentThread().isInterrupted() ? "interrupted" : sum));
        }
    }
}
