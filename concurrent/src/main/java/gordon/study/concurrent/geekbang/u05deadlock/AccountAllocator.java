package gordon.study.concurrent.geekbang.u05deadlock;

import java.util.HashSet;
import java.util.Set;

public class AccountAllocator {

    private Set<Object> als = new HashSet<>();

    // 一次性申请所有资源
    public synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
        }
        return true;
    }

    // 归还资源
    public synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}
