package gordon.study.concurrent.geekbang.u23future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallelize {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        try {
            Future f1 = es.submit(() -> System.out.println("get price by S1 and save"));
            Future f2 = es.submit(() -> System.out.println("get price by S2 and save"));
            Future f3 = es.submit(() -> System.out.println("get price by S3 and save"));
            f1.get();
            f2.get();
            f3.get();
            System.out.println("get all prices");
        } catch (Exception e) {
            System.err.println("get price error");
        } finally {
            es.shutdown();
        }
    }
}
