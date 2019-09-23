package gordon.study.jvm.alpha.gc;

/**
 * -Xmx20m -Xms20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
 */
public class NewSizeDemo1 {

    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}

/*
-Xmn 设置新生代大小
-XX:SurvivorRatio 设置新生代中 eden 空间和 from/to 空间的比例。默认值8，即 eden:from:to = 8:1:1，eden 约占 80% 新生代空间（考虑到 survivor 空间有对齐操作）

本例 eden 区 512KB，总可用新生代 512 + 256 = 768KB。

[GC (Allocation Failure) [DefNew: 512K->255K(768K), 0.0007472 secs] 512K->434K(20224K), 0.0007838 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [DefNew: 767K->108K(768K), 0.0007847 secs] 946K->542K(20224K), 0.0008020 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [DefNew: 620K->172K(768K), 0.0006038 secs] 1054K->605K(20224K), 0.0006256 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
Heap
 def new generation   total 768K, used 677K [0x00000000fec00000, 0x00000000fed00000, 0x00000000fed00000)
  eden space 512K,  98% used [0x00000000fec00000, 0x00000000fec7e5c8, 0x00000000fec80000)
  from space 256K,  67% used [0x00000000fecc0000, 0x00000000feceb1a8, 0x00000000fed00000)
  to   space 256K,   0% used [0x00000000fec80000, 0x00000000fec80000, 0x00000000fecc0000)
 tenured generation   total 19456K, used 10673K [0x00000000fed00000, 0x0000000100000000, 0x0000000100000000)
   the space 19456K,  54% used [0x00000000fed00000, 0x00000000ff76c6b8, 0x00000000ff76c800, 0x0000000100000000)
 Metaspace       used 3495K, capacity 4498K, committed 4864K, reserved 1056768K
  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K

由于 eden 区无法容纳1MB数组，故触发新生代GC；新生代空间不足，数组直接分配在老年代。

!!PS:JDK1.8 默认用 -XX:+UseParallelGC，from space = to space = 512K，经试验，将 -Xmn 设置大一点即正常，设置非常小实际也是分配 512K，猜测是该GC保证最小空间 512K。
 */