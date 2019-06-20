package gordon.study.algorithm.algs4.stackqueue.practice;

import gordon.study.algorithm.algs4.stackqueue.Stack;

import java.util.Scanner;

public class InfixToPostfix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/stackqueue/evaluate.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String postfix = infixToPostfix(line);
            System.out.printf("%s -> %s : %.2f\n", line, postfix, EvaluatePostfix.evaluate(postfix));
        }
        scanner.close();
    }

    private static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();


        StringBuilder number = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    result.append(number).append(' ');
                    number.setLength(0);
                }

                if (isOperator(c)) {
                    stack.push(c);
                } else if (c == ')') {
                    result.append(stack.pop()).append(' ');
                }
            }
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
