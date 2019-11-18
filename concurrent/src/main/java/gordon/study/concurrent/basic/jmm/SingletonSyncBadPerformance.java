package gordon.study.concurrent.basic.jmm;

public class SingletonSyncBadPerformance {

    private static SingletonSyncBadPerformance instance;

    public synchronized static SingletonSyncBadPerformance getInstance() {
        if (instance == null) // 1
            instance = new SingletonSyncBadPerformance();
        return instance;
    }

}

/*
性能差，但的确是正确的。
 */