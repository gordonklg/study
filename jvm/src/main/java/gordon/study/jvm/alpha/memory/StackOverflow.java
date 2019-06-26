package gordon.study.jvm.alpha.memory;

/**
 * -Xss128k   893
 * -Xss256k   1978
 * -Xss512k   4201
 */
public class StackOverflow {

    public static void main(String[] args) {
        infiniteRecursive(1);

    }

    public static void infiniteRecursive(long a) {
        System.out.println(a);
        infiniteRecursive(++a);
    }
}
