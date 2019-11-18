package gordon.study.concurrent.scene.producerconsumer;

public class Wrong implements Storage {

    private static final int BUF_SZ = 10;

    private int[] buffer = new int[BUF_SZ];

    private int pos;

    @Override
    public void produce(int val) throws InterruptedException {
        while (pos == BUF_SZ) {
            Thread.sleep(1);
        }
        buffer[pos++] = val;
    }

    @Override
    public int consume() throws InterruptedException {
        while (pos == 0) {
            Thread.sleep(1);
        }
        return buffer[--pos];
    }

    @Override
    public int getSize() {
        return pos;
    }

    public static void main(String[] args) throws Exception {
        Wrong storage = new Wrong();
        storage.run();
    }

}