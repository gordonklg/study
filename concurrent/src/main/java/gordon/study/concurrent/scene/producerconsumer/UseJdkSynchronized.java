package gordon.study.concurrent.scene.producerconsumer;

/*
这并不是OS课上生产着消费者问题的标准解法。这本质上只是个普通的临界区问题解法。
 */
public class UseJdkSynchronized implements Storage {

    private static final int BUF_SZ = 10;

    private int[] buffer = new int[BUF_SZ];

    private int pos;

    @Override
    public void produce(int val) throws InterruptedException {
        synchronized (buffer) { // 由于 buffer 和 pos 变量都只在同步块中改变，因此用哪个当锁都一样。new一个Object mutex当锁可能更容易理解。
                                // 将 synchronized 加到方法上也是可以的，这时对象锁是 UseJdkSynchronized 的实例。
            while (pos == BUF_SZ) {
                buffer.wait();
            }
            buffer[pos++] = val;
            buffer.notifyAll();
        }
    }

    @Override
    public int consume() throws InterruptedException {
        int val = 0;
        synchronized (buffer) {
            while (pos == 0) {
                buffer.wait();
            }
            val = buffer[--pos];
            buffer.notifyAll();
        }
        return val;
    }

    @Override
    public int getSize() {
        return pos;
    }

    public static void main(String[] args) throws Exception {
        UseJdkSynchronized storage = new UseJdkSynchronized();
        storage.run();
    }

}