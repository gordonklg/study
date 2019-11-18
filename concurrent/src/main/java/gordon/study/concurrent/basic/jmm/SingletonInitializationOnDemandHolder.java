package gordon.study.concurrent.basic.jmm;

public class SingletonInitializationOnDemandHolder {

    private static class InstanceHolder {
        public static SingletonInitializationOnDemandHolder instance = new SingletonInitializationOnDemandHolder();
    }

    public static SingletonInitializationOnDemandHolder getInstance() {
        return InstanceHolder.instance;
    }

}

/*
这个方案的实质是：允许“问题的根源”的三行伪代码中的 2 和 3 重排序，但不允许非构造线程（这里指线程 B）“看到”这个重排序。

初始化一个类，包括执行这个类的静态初始化和初始化在这个类中声明的静态字段。根据 java 语言规范，在首次发生下列任意一种情况时，一个类或接口类型 T 将被立即初始化：
T 是一个类，而且一个 T 类型的实例被创建；
T 是一个类，且 T 中声明的一个静态方法被调用；
T 中声明的一个静态字段被赋值；
T 中声明的一个静态字段被使用，而且这个字段不是一个常量字段；
T 是一个顶级类，而且一个断言语句嵌套在 T 内部被执行。

在 InstanceFactory 示例代码中，首次执行 getInstance() 的线程将导致 InstanceHolder 类被初始化（符合情况 4）。

由于 java 语言是多线程的，多个线程可能在同一时间尝试去初始化同一个类或接口（比如这里多个线程可能在同一时刻调用 getInstance() 来初始化 InstanceHolder 类）。
因此在 java 中初始化一个类或者接口时，需要做细致的同步处理。
Java 语言规范规定，对于每一个类或接口 C，都有一个唯一的初始化锁 LC 与之对应。从 C 到 LC 的映射，由 JVM 的具体实现去自由实现。JVM 在类初始化期间会获取这个
初始化锁，并且每个线程至少获取一次锁来确保这个类已经被初始化过了（事实上，java 语言规范允许 JVM 的具体实现在这里做一些优化）。
 */

