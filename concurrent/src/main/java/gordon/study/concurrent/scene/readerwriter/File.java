package gordon.study.concurrent.scene.readerwriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public interface File {

    void write(int num) throws InterruptedException;

    int read() throws InterruptedException;

//    boolean checkResult()

    default void run() throws Exception {
        ExecutorService esr = Executors.newFixedThreadPool(10);
        ExecutorService esw = Executors.newFixedThreadPool(10);
        Future<Integer>[] future = new Future[10];
        try {
            for (int i = 0; i < 10; i++) {
                future[i] = esr.submit(new Reader(this));
            }
            for (int i = 0; i < 10; i++) {
                esw.submit(new Writer(this));
            }
            for (Future<Integer> f : future) {
                f.get();
            }
        } finally {
            esr.shutdown();
            esw.shutdown();
        }
    }
}
