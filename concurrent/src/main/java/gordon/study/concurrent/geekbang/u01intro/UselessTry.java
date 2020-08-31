package gordon.study.concurrent.geekbang.u01intro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UselessTry {

    private static final int POOL_SIZE = 100;

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            Singleton s = Singleton.getInstance();
            if (s.getObj() == null) {
                System.err.println("NULL!");
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            es.submit(r);
            TimeUnit.MILLISECONDS.sleep(10);
        }
        es.shutdown();
    }
}
