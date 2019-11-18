package gordon.study.concurrent.basic.jmm;

public class VolatileSample {

    private volatile long vl;

    public void set(long l) {
        vl = l;   // 单个 volatile 变量的写
    }

    public void getAndIncrement() {
        vl++;    // 复合（多个）volatile 变量的读 / 写
    }

    public long get() {
        return vl;   // 单个 volatile 变量的读
    }
}
