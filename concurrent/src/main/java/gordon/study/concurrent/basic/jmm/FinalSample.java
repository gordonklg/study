package gordon.study.concurrent.basic.jmm;

public class FinalSample {

    private int i;                            // 普通变量

    private final int j;                      //final 变量

    private static FinalSample obj;

    public FinalSample() {     // 构造函数
        i = 1;                        // 写普通域
        j = 2;                        // 写 final 域
    }

    public static void writer() {    // 写线程 A 执行
        obj = new FinalSample();
    }

    public static void reader() {       // 读线程 B 执行
        FinalSample object = obj;       // 读对象引用
        int a = object.i;                // 读普通域
        int b = object.j;                // 读 final 域
    }
}
