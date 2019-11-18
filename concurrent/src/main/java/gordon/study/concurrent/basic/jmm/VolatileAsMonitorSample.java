package gordon.study.concurrent.basic.jmm;

public class VolatileAsMonitorSample {

    private int a;

    private volatile boolean flag;

    public void writer() {
        a = 1;                   //1
        flag = true;               //2
    }

    public void reader() {
        if (flag) {                //3
            int i = a;           //4
        }
    }
}

/*
假设线程 A 执行 writer() 方法之后，线程 B 执行 reader() 方法。根据 happens before 规则，这个过程建立的 happens before 关系可以分为两类：
根据程序次序规则，1 happens before 2; 3 happens before 4。
根据 volatile 规则，2 happens before 3。
根据 happens before 的传递性规则，1 happens before 4。
 */