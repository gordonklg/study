package gordon.study.algorithm.clrs.stringmatching.naive;

import java.util.ArrayList;
import java.util.List;

public class NaiveStringMatching {

    public static void main(String[] args) {
        NaiveStringMatching inst = new NaiveStringMatching();
        printIntArray(inst.match("aaaa", "aa")); // 0 1 2
        printIntArray(inst.match("aaab", "ab")); // 2
        printIntArray(inst.match("aaab", "abc")); //
        printIntArray(inst.match("ababa", "bab")); // 1
        printIntArray(inst.match("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaab")); // worst case. O((n-m+1)m)
    }

    private static void printIntArray(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public Integer[] match(String source, String pattern) {
        List<Integer> tempResult = new ArrayList<>();
        for (int i = 0; i < source.length() - pattern.length() + 1; i++) {
//            if (pattern.equals(source.substring(i, i + pattern.length()))) {
//                tempResult.add(i);
//            }
            int j = 0;
            for (; j < pattern.length(); j++) {
                if (pattern.charAt(j) != source.charAt(i + j)) {
                    break;
                }
            }
            if (j == pattern.length()) {
                tempResult.add(i);
            }
        }
        return tempResult.toArray(new Integer[0]);
    }
}
