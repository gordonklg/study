package gordon.study.jvm.alpha.memory;

/**
 * -Xmx50m
 */
public class TooManyThreads {

    public static void main(String[] args) {
        for (int i = 0; i < 1024; i++) {
            System.out.println(i);
            new Thread(() -> {
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                }
            }).start();
        }
    }
}

/*
不会抛异常。线程的栈空间也是在堆外分配的，因此和直接内存非常相似。
如果想让系统支持更多的线程，那么应该使用一个较小的堆空间。
 */