package gordon.study.concurrent.scene.producerconsumer;

import java.util.concurrent.Semaphore;

public class UseSemaphore implements Storage {

    private static final int BUF_SZ = 10;

    private int[] buffer = new int[BUF_SZ];

    private int pos;

    private Semaphore notFull = new Semaphore(BUF_SZ);

    private Semaphore notEmpty = new Semaphore(0);

    private Semaphore mutex = new Semaphore(1);

    @Override
    public void produce(int val) throws InterruptedException {
        try {
            notFull.acquire();
            mutex.acquire();
            buffer[pos++] = val;
        } finally {
            mutex.release();
            notEmpty.release();
        }
    }

    @Override
    public int consume() throws InterruptedException {
        try {
            notEmpty.acquire();
            mutex.acquire();
            return buffer[--pos];
        } finally {
            mutex.release();
            notFull.release();
        }
    }

    @Override
    public int getSize() {
        return pos;
    }

    public static void main(String[] args) throws Exception {
        UseSemaphore storage = new UseSemaphore();
        storage.run();
    }

}