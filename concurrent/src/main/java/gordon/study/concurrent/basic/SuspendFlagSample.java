package gordon.study.concurrent.basic;

public class SuspendFlagSample {

    public static void main(String[] args) throws Exception {
        SuspendFlagSample instance = new SuspendFlagSample();
        instance.work();
    }

    @SuppressWarnings("deprecation")
    private void work() throws Exception {
        SumThread t = new SumThread();
        t.start();
        Thread.sleep(100);
        t.suspendIt();
        Thread.sleep(100);
        System.out.println(t.getState());
        System.out.println(t.getSum());
        Thread.sleep(100);
        System.out.println(t.getSum());
        t.resumeIt();
        Thread.sleep(100);
        System.out.println(t.getState());
        System.out.println(t.getSum());
        t.stop();
    }

    private class SumThread extends Thread {

        private volatile boolean suspend = false;

        private long sum;

        @Override
        public void run() {
            for (int i = 0; i < Long.MAX_VALUE; i++) {
                synchronized (this) {
                    while (suspend) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                sum += i;
            }
        }

        public void suspendIt() {
            suspend = true;
        }

        public void resumeIt() {
            suspend = false;
            synchronized (this) {
                notify();
            }
        }

        public long getSum() {
            return sum;
        }
    }
}
