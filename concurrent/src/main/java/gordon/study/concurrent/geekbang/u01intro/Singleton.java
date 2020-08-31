package gordon.study.concurrent.geekbang.u01intro;

public class Singleton {

    private Object obj;

    private Singleton() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        obj = new Object();
    }

    public Object getObj() {
        return obj;
    }

    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}