package gordon.study.snippet.jdk.basic;

public class ModOperation {

    public static void main(String[] args) {
        // ===========取余运算==========
        // 取余运算在取整数商的值时，向 0 方向舍入。
        // 例如，-13/10=-1.3，向0舍入为-1
        System.out.println(13 / 10); // 1
        System.out.println(-13 / 10); // -1
        System.out.println(13 / -10); // -1
        System.out.println(-13 / -10); // 1

        System.out.println(13 % 10); // 3
        System.out.println(-13 % 10); // -3
        System.out.println(13 % -10); // 3  -- (-1) * (-10) + 3 = 13
        System.out.println(-13 % -10); // -3

        System.out.println(0 / 10); // 0
        System.out.println(-0 / 10); // 0

        // ===========取模运算==========
        // 取余运算在取整数商的值时，向负无穷方向舍入。（类似于 floor 函数）
        System.out.println(Math.floorDiv(13, 10)); // 1
        System.out.println(Math.floorDiv(-13, 10)); // -2
        System.out.println(Math.floorDiv(13, -10)); // -2
        System.out.println(Math.floorDiv(-13, -10)); // 1

        System.out.println(Math.floorMod(13, 10)); // 3
        System.out.println(Math.floorMod(-13, 10)); // 7
        System.out.println(Math.floorMod(13, -10)); // -7  -- (-2) * (-10) + (-7) = 13
        System.out.println(Math.floorMod(-13, -10)); // -3
    }
}
