package gordon.study.snippet.jdk.basic;

public class Infinity {

    public static void main(String[] args) {
        double infinity = 1.0 / 0.0;
        double nan = 0.0 / 0.0;
        System.out.println(infinity);
        System.out.println(nan);
        System.out.println(infinity > 1);
        System.out.println(infinity < 1);
        System.out.println(infinity == 2.0 / 0.0);
        System.out.println(nan < infinity);
        System.out.println(nan == nan);

        System.out.println(infinity - 100);
        System.out.println(infinity / 0.0);

        System.out.println(-infinity);
        System.out.println(-1.0 / 0.0);
        System.out.println(1.0 / -0.0);
        System.out.println(-infinity < infinity);
    }
}
