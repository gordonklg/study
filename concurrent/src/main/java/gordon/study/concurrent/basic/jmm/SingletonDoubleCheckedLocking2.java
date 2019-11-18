package gordon.study.concurrent.basic.jmm;

public class SingletonDoubleCheckedLocking2 {

    private volatile static SingletonDoubleCheckedLocking2 instance;

    public static SingletonDoubleCheckedLocking2 getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleCheckedLocking2.class) {
                if (instance == null)
                    instance = new SingletonDoubleCheckedLocking2();
            }
        }
        return instance;
    }

}

/*
当声明对象的引用为 volatile 后，“问题的根源”的三行伪代码中的 2 和 3 之间的重排序，在多线程环境中将会被禁止。（volatile写）
 */