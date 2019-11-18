package gordon.study.concurrent.basic.jmm;

public class SingletonBad {

    private static SingletonBad instance;

    public static SingletonBad getInstance() {
        if (instance == null) // 1
            instance = new SingletonBad();
        return instance;
    }

}

/*
明显存在问题的代码。多个线程通过注释1所在行，创建了多个实例。
 */