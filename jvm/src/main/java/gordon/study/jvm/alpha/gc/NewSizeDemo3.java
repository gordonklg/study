package gordon.study.jvm.alpha.gc;

/**
 * -Xmx20m -Xms20m -Xmn15m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+UseSerialGC
 */
public class NewSizeDemo3 {

    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 8; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}

/*
Heap
 def new generation   total 13824K, used 11000K [0x00000000fec00000, 0x00000000ffb00000, 0x00000000ffb00000)
  eden space 12288K,  89% used [0x00000000fec00000, 0x00000000ff6be048, 0x00000000ff800000)
  from space 1536K,   0% used [0x00000000ff800000, 0x00000000ff800000, 0x00000000ff980000)
  to   space 1536K,   0% used [0x00000000ff980000, 0x00000000ff980000, 0x00000000ffb00000)
 tenured generation   total 5120K, used 0K [0x00000000ffb00000, 0x0000000100000000, 0x0000000100000000)
   the space 5120K,   0% used [0x00000000ffb00000, 0x00000000ffb00000, 0x00000000ffb00200, 0x0000000100000000)
 Metaspace       used 3494K, capacity 4498K, committed 4864K, reserved 1056768K
  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K

所有分配都在 eden 区。没有触发任何 GC 行为。（代码修改为创建8个数组，否则会Minor GC）
 */