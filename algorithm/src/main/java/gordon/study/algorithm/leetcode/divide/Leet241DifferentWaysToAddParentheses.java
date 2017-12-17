package gordon.study.algorithm.leetcode.divide;

import java.util.ArrayList;
import java.util.List;

public class Leet241DifferentWaysToAddParentheses {

    public static void main(String[] args) {
        Leet241DifferentWaysToAddParentheses inst = new Leet241DifferentWaysToAddParentheses();
        System.out.println(inst.diffWaysToCompute("2*4"));
        System.out.println(inst.diffWaysToCompute("2*4+3"));
        System.out.println(inst.diffWaysToCompute("2*3-4*5"));
        System.out.println(inst.diffWaysToCompute("2*4+6*3-11-2*5"));
    }

    List<Integer> diffWaysToCompute(String input) {
        char[] chars = input.toCharArray();
        List<Integer> numbers = new ArrayList<>();
        List<Character> opers = new ArrayList<>();
        int lastPos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*') {
                numbers.add(Integer.parseInt(input.substring(lastPos, i)));
                opers.add(chars[i]);
                lastPos = i + 1;
            }
        }
        numbers.add(Integer.parseInt(input.substring(lastPos)));
        return diffWaysToCompute(numbers.toArray(new Integer[0]), opers.toArray(new Character[0]), 0, numbers.size() - 1);
    }

    List<Integer> diffWaysToCompute(Integer[] numbers, Character[] opers, int start, int end) {
        List<Integer> result = new ArrayList<>();
        if (start == end) {
            result.add(numbers[start]);
            return result;
        }
        for (int i = start; i < end; i++) {
            List<Integer> leftPart = diffWaysToCompute(numbers, opers, start, i);
            List<Integer> rightPart = diffWaysToCompute(numbers, opers, i + 1, end);
            for (Integer left : leftPart) {
                for (Integer right : rightPart) {
                    if (opers[i] == '+') {
                        result.add(left + right);
                    } else if (opers[i] == '-') {
                        result.add(left - right);
                    } else {
                        result.add(left * right);
                    }
                }
            }
        }
        return result;
    }
}
