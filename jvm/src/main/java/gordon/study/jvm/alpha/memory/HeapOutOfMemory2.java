package gordon.study.jvm.alpha.memory;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xmx30m
 */
public class HeapOutOfMemory2 {

    public static void main(String[] args) {
        List<Object> list = new LinkedList<>();
        long count = 0;
        while (true) {
            if (++count % 10000 == 0) {
                System.out.println(count);
            }
            list.add(new Object());
        }
    }
}

/*
Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
发生在GC占用大量时间为释放很小空间的时候发生的，是一种保护机制。一般是因为堆太小，导致异常的原因：没有足够的内存。
Sun 官方对此的定义：超过98%的时间用来做GC并且回收了不到2%的堆内存时会抛出此异常。

-XX:-UseGCOverheadLimit 可以禁用该检查
禁用后很快大量时间都用在gc上了。最终抛异常：
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */