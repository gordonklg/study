package gordon.study.concurrent.scene.producerconsumer;

import java.util.concurrent.Callable;

public class Producer implements Callable {

    private Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 10000; i++) {
            storage.produce(i);
        }
        return null;
    }
}