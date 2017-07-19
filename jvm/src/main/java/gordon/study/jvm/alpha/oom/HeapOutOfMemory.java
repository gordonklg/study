package gordon.study.jvm.alpha.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xmx30m
 */
public class HeapOutOfMemory {

    public static void main(String[] args) {
        List<Object> list = new LinkedList<>();
        long count = 0;
        while (true) {
            if(++count % 10000 == 0) {
                System.out.println(count);
            }
            list.add(new Object());
        }
    }
}
