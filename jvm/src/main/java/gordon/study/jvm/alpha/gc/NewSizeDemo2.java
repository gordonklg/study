package gordon.study.jvm.alpha.gc;

/**
 * -Xmx20m -Xms20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
 */
public class NewSizeDemo2 {

    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}

/*
[GC (Allocation Failure) [DefNew: 3117K->1710K(5376K), 0.0023177 secs] 3117K->1710K(18688K), 0.0023649 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [DefNew: 4848K->1024K(5376K), 0.0017643 secs] 4848K->1700K(18688K), 0.0017851 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [DefNew: 4156K->1024K(5376K), 0.0003307 secs] 4832K->1700K(18688K), 0.0003474 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
Heap
 def new generation   total 5376K, used 4231K [0x00000000fec00000, 0x00000000ff300000, 0x00000000ff300000)
  eden space 3584K,  89% used [0x00000000fec00000, 0x00000000fef21ec8, 0x00000000fef80000)
  from space 1792K,  57% used [0x00000000ff140000, 0x00000000ff240010, 0x00000000ff300000)
  to   space 1792K,   0% used [0x00000000fef80000, 0x00000000fef80000, 0x00000000ff140000)
 tenured generation   total 13312K, used 676K [0x00000000ff300000, 0x0000000100000000, 0x0000000100000000)
   the space 13312K,   5% used [0x00000000ff300000, 0x00000000ff3a9188, 0x00000000ff3a9200, 0x0000000100000000)
 Metaspace       used 3495K, capacity 4498K, committed 4864K, reserved 1056768K
  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K

数组分配在 eden 区，直到 eden 区空间不足发生新生代GC。GC会回收数组，这些数组不会进入老年代。
 */