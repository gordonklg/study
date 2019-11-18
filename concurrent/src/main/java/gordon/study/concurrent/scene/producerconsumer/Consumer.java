package gordon.study.concurrent.scene.producerconsumer;

import java.util.concurrent.Callable;

public class Consumer implements Callable<Integer> {

    private Storage storage;

    private int sum = 0;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < 10000; i++) {
            sum += storage.consume();
        }
        return sum;
    }
}