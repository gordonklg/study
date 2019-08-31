package gordon.study.jvm.alpha.memory;

/**
 * -Xmx50m
 */
public class ArrayTooLarge {

    public static void main(String[] args) {
        int[] small = new int[1000];
        System.out.println("1000 size is OK.");
        int[] large = new int[30 * 1024 * 1024 / 4];
        System.out.println("OK. Now GC...");
        large = null; // must set large to null, or OOM.
        large = new int[30 * 1024 * 1024 / 4];
        System.out.println("Still OK. Now GC...");
        large = null;
        int[] huge = new int[50 * 1024 * 1024 / 4];
        System.out.println("Never sees me.");
    }
}

/*
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */