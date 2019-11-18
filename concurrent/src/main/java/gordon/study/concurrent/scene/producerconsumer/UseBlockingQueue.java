package gordon.study.concurrent.scene.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UseBlockingQueue implements Storage {

    private static final int BUF_SZ = 10;

    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

    @Override
    public void produce(int val) throws InterruptedException {
        queue.put(val);
    }

    @Override
    public int consume() throws InterruptedException {
        return queue.take();
    }

    @Override
    public int getSize() {
        return queue.size();
    }

    public static void main(String[] args) throws Exception {
        UseBlockingQueue storage = new UseBlockingQueue();
        storage.run();
    }

}