package gordon.study.jvm.alpha.gc;

/**
 * -Xmx20m -Xms20m -XX:NewRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
 */
public class NewSizeDemo4 {

    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}

/*
-XX:NewRatio 设置老年代空间和新生代空间的比例。默认值为2，即老年代：新生代=2:1。

[GC (Allocation Failure) [DefNew: 5251K->640K(6144K), 0.0024358 secs] 5251K->1710K(19840K), 0.0024863 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [DefNew: 5864K->0K(6144K), 0.0016051 secs] 6934K->2734K(19840K), 0.0016223 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
Heap
 def new generation   total 6144K, used 2201K [0x00000000fec00000, 0x00000000ff2a0000, 0x00000000ff2a0000)
  eden space 5504K,  40% used [0x00000000fec00000, 0x00000000fee267a8, 0x00000000ff160000)
  from space 640K,   0% used [0x00000000ff160000, 0x00000000ff160000, 0x00000000ff200000)
  to   space 640K,   0% used [0x00000000ff200000, 0x00000000ff200000, 0x00000000ff2a0000)
 tenured generation   total 13696K, used 2734K [0x00000000ff2a0000, 0x0000000100000000, 0x0000000100000000)
   the space 13696K,  19% used [0x00000000ff2a0000, 0x00000000ff54b858, 0x00000000ff54ba00, 0x0000000100000000)
 Metaspace       used 3495K, capacity 4498K, committed 4864K, reserved 1056768K
  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K

由于survivor空间不足1M，影响了新生代的正常回收，需要老年代进行空间担保。
第一次新生代GC时，eden区可能有4个数组。本来GC会回收掉3个，并将另一个放入survivor区，但是survivor区空间不足，因此直接放入老年代。两次新生代GC导致两个数组进入老年代。
 */