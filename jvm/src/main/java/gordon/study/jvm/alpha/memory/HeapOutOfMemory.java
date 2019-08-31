package gordon.study.jvm.alpha.memory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * -Xmx30m
 */
public class HeapOutOfMemory {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            System.out.println(i);
            list.add(new byte[1024 * 1024]);
        }
    }
}

/*
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */