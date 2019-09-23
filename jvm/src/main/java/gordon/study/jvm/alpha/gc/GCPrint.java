package gordon.study.jvm.alpha.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xmx30m
 * -XX:+PrintGCDetails
 */
public class GCPrint {

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
[GC (Allocation Failure) [PSYoungGen: 8192K->1016K(9216K)] 8192K->6760K(29696K), 0.1034539 secs] [Times: user=0.00 sys=0.00, real=0.10 secs]
// [Minor GC (本次引起GC的原因是在年轻代中没有足够的空间能够存储新的数据) [] GC前堆空间使用量->GC后堆空间使用量(当前堆空间总和), 本次GC所花时间]
// [Times: 用户态CPU耗时，系统CPU耗时，GC实际经历的时间]
[GC (Allocation Failure) [PSYoungGen: 9208K->1000K(9216K)] 14952K->14952K(29696K), 0.0282458 secs] [Times: user=0.06 sys=0.00, real=0.03 secs]
[Full GC (Ergonomics) [PSYoungGen: 1000K->0K(9216K)] [ParOldGen: 13952K->14862K(20480K)] 14952K->14862K(29696K), [Metaspace: 3497K->3497K(1056768K)], 0.4108382 secs] [Times: user=0.74 sys=0.02, real=0.41 secs]
...
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 20350K->20350K(20480K)] 28542K->28542K(29696K), [Metaspace: 3500K->3500K(1056768K)], 0.0758928 secs] [Times: user=0.31 sys=0.00, real=0.08 secs]
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 20352K->20352K(20480K)] 28544K->28544K(29696K), [Metaspace: 3500K->3500K(1056768K)], 0.0801597 secs] [Times: user=0.28 sys=0.00, real=0.08 secs]
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 20353K->20353K(20480K)] 28545K->28545K(29696K), [Metaspace: 3500K->3500K(1056768K)], 0.0753435 secs] [Times: user=0.27 sys=0.00, real=0.08 secs]
// 抛异常 Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
// 然后最后一次GC就放飞自我了？空间都被释放了
[Full GC (Ergonomics) [PSYoungGen: 8191K->0K(9216K)] [ParOldGen: 20369K->659K(17920K)] 28561K->659K(27136K), [Metaspace: 3525K->3525K(1056768K)], 0.0074896 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
// -XX:+PrintGCDetails会在虚拟机退出前打印堆的详细信息（然而因为最后一次GC，堆信息看起来很健康……）
Heap
 PSYoungGen      total 9216K, used 225K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 2% used [0x00000000ff600000,0x00000000ff638560,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 17920K, used 659K [0x00000000fe200000, 0x00000000ff380000, 0x00000000ff600000)
  object space 17920K, 3% used [0x00000000fe200000,0x00000000fe2a4d70,0x00000000ff380000)
 Metaspace       used 3532K, capacity 4502K, committed 4864K, reserved 1056768K
  class space    used 391K, capacity 394K, committed 512K, reserved 1048576K
 */

/*
加上JVM参数：-XX:-UseGCOverheadLimit （参考 memory.HeapOutOfMemory2）
最终堆信息还是类似上面，看不出问题。
 */
