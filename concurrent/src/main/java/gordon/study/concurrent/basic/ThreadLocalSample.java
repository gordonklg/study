package gordon.study.concurrent.basic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class ThreadLocalSample {

    public static void main(String[] args) throws Exception {
        ThreadLocalSample instance = new ThreadLocalSample();
        instance.work();
    }

    private void work() throws Exception {
        RandomAverageCalculator calculator = new RandomAverageCalculator();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                calculator.calculate();
            }
        };
        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
    }

    private class RandomAverageCalculator {

        private ThreadLocal<Random> random = new ThreadLocal<>();

        private ThreadLocal<NumberFormat> nf = new ThreadLocal<>();

        public void calculate() {
            long sum = 0;
            if (random.get() == null) {
                random.set(new Random());
            }
            if (nf.get() == null) {
                nf.set(new DecimalFormat("#,##0.0"));
            }
            for (int i = 0; i < 100; i++) {
                sum += random.get().nextInt(10000);
            }
            System.out.println("Average value: " + nf.get().format(sum / 100.0));
        }
    }
}
