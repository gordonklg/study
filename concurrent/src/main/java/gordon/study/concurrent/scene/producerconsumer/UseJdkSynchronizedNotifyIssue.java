package gordon.study.concurrent.scene.producerconsumer;

public class UseJdkSynchronizedNotifyIssue implements Storage {

    private static final int BUF_SZ = 1; // 一个小的缓冲区容易重现错误

    private int[] buffer = new int[BUF_SZ];

    private int pos;

    @Override
    public void produce(int val) throws InterruptedException {
        synchronized (buffer) {
            while (pos == BUF_SZ) {
                buffer.wait();
            }
            buffer[pos++] = val;
            buffer.notify(); // just notify
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
            buffer.notify(); // just notify
        }
        return val;
    }

    @Override
    public int getSize() {
        return pos;
    }

    public static void main(String[] args) throws Exception {
        UseJdkSynchronizedNotifyIssue storage = new UseJdkSynchronizedNotifyIssue();
        storage.run();
    }

}

/*
出问题的一种可能场景：
当前缓冲区为空，所有消费者都在等待锁资源，一个生产者获得锁资源，并成功向缓冲区放入一个数据，然后调用notify唤醒一个等待该锁资源的生产者线程。
新唤醒的生产者线程发现缓冲区已满，只能释放锁资源并进入等待队列。这下，没有任何线程有机会被唤醒了，20个工作线程全部进入等待状态。

  java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076c322090> (a [I)
 */