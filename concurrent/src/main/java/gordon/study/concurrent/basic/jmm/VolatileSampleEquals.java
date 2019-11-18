package gordon.study.concurrent.basic.jmm;

public class VolatileSampleEquals {

    private long vl;               // 64 位的 long 型普通变量

    public synchronized void set(long l) {     // 对单个的普通 变量的写用同一个监视器同步
        vl = l;
    }

    public void getAndIncrement() { // 普通方法调用
        long temp = get();           // 调用已同步的读方法
        temp += 1L;                  // 普通写操作
        set(temp);                   // 调用已同步的写方法
    }

    public synchronized long get() {
        // 对单个的普通变量的读用同一个监视器同步
        return vl;
    }

}
