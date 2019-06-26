package gordon.study.jvm.alpha.memory;

/**
 * -Xss128k
 *
 * 因为调用栈深度大约等于 main 中调用 fib 的入参值。
 */
public class WhyNotStackOverflow {

    private static long count = 0;

    public static void main(String[] args) {
        /**
         * 如果值够大，例如10000，就会抛StackOvreflow。
         */
        System.out.println(fib(100));
    }

    public static long fib(long num) {
        System.out.println(++count);
        if (num < 3) {
            return 1;
        }
        return fib(num - 1) + fib(num - 2);
    }
}
