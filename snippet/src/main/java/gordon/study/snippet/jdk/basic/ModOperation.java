package gordon.study.snippet.jdk.basic;

public class ModOperation {

    public static void main(String[] args) {
        System.out.println(13 / 10);
        System.out.println(-13 / 10);
        System.out.println(13 / -10);
        System.out.println(-13 / -10);

        System.out.println(13 % 10);
        System.out.println(-13 % 10);
        System.out.println(13 % -10);
        System.out.println(-13 % -10);

        // 从结果看，相当于先不考虑符号求模，然后决定符号，再根据模求出余数。

        System.out.println(0 / 10);
        System.out.println(-0 / 10);
    }
}
