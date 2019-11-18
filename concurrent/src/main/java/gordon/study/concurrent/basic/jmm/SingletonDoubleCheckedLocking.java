package gordon.study.concurrent.basic.jmm;

public class SingletonDoubleCheckedLocking {

    private static SingletonDoubleCheckedLocking instance;

    public static SingletonDoubleCheckedLocking getInstance() {
        if (instance == null) {                                         //4: 第一次检查
            synchronized (SingletonDoubleCheckedLocking.class) {        //5: 加锁
                if (instance == null)                                   //6: 第二次检查
                    instance = new SingletonDoubleCheckedLocking();     //7: 问题的根源出在这里
            }                                                           //8
        }                                                               //9
        return instance;                                                //10
    }

}

/*
双重检查锁定（double-checked locking）。通过双重检查锁定来降低同步的开销。聪明但是是错误的！！！

问题的根源：
instance = new SingletonDoubleCheckedLocking(); 可以分解为如下伪代码
    memory = allocate();   //1：分配对象的内存空间
    ctorInstance(memory);  //2：初始化对象
    instance = memory;     //3：设置 instance 指向刚分配的内存地址

上面三行伪代码中的 2 和 3 之间，可能会被重排序（在一些 JIT 编译器上，这种重排序是真实发生的）。

因此，有可能线程A执行到第7行，分配了对象的内存空间，由于重排序将instance 指向刚分配的内存地址但尚未初始化对象，这是，线程B执行到第4行，
返回了一个尚未初始化的对象。

 */