package gordon.study.concurrent.scene.producerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public interface Storage {

    void produce(int val) throws InterruptedException;

    int consume() throws InterruptedException;

    int getSize();

    default void run() throws Exception {
        ExecutorService esp = Executors.newFixedThreadPool(10);
        ExecutorService esc = Executors.newFixedThreadPool(10);
        Future<Integer>[] future = new Future[10];
        int sum = 0;
        try {
            for (int i = 0; i < 10; i++) {
                esp.submit(new Producer(this));
            }
            for (int i = 0; i < 10; i++) {
                future[i] = esc.submit(new Consumer(this));
            }
            for (Future<Integer> f : future) {
                sum += f.get();
            }
        } finally {
            esp.shutdown();
            esc.shutdown();
            System.out.println(sum);
            System.out.println(getSize());
        }
    }
}
