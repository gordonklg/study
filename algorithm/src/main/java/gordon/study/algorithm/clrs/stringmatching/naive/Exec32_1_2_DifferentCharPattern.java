package gordon.study.algorithm.clrs.stringmatching.naive;

import java.util.ArrayList;
import java.util.List;

public class Exec32_1_2_DifferentCharPattern {

    public static void main(String[] args) {
        Exec32_1_2_DifferentCharPattern inst = new Exec32_1_2_DifferentCharPattern();
        printIntArray(inst.match("abc", "abc")); // 0
        printIntArray(inst.match("aaababcabbabcc", "abc")); // 4 10
    }

    private static void printIntArray(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public Integer[] match(String source, String pattern) {
        List<Integer> tempResult = new ArrayList<>();
        for (int i = 0; i < source.length() - pattern.length() + 1; ) {
            int matched = 0;
            for (; matched < pattern.length(); matched++) {
                if (pattern.charAt(matched) != source.charAt(i + matched)) {
                    break;
                }
            }
            if (matched == pattern.length()) {
                tempResult.add(i);
            }
            i += Math.max(matched, 1);
        }
        return tempResult.toArray(new Integer[0]);
    }
}
