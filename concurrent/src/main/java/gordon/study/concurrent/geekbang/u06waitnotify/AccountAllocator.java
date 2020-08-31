package gordon.study.concurrent.geekbang.u06waitnotify;

import java.util.HashSet;
import java.util.Set;

public class AccountAllocator {

    private Set<Object> als = new HashSet<>();

    // 一次性申请所有资源
    public synchronized void apply(Object from, Object to) {
        while (als.contains(from) || als.contains(to)) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        als.add(from);
        als.add(to);
    }

    // 归还资源
    public synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
        notifyAll();
    }
}
