package gordon.study.algorithm.algs4.stackqueue.practice;

import gordon.study.algorithm.algs4.stackqueue.Stack;

import java.util.Scanner;

public class EvaluatePostfix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/stackqueue/evaluatePostfix.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.printf("%s = %.2f\n", line, evaluate(line));
        }
        scanner.close();
    }

    public static double evaluate(String line) {
        String[] tokens = line.split(" ");
        Stack<Double> operands = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (!isOperator(token)) {
                operands.push(Double.parseDouble(token));
            } else {
                double val = operands.pop();
                if (token.equals("+")) {
                    val = operands.pop() + val;
                } else if (token.equals("-")) {
                    val = operands.pop() - val;
                } else if (token.equals("*")) {
                    val = operands.pop() * val;
                } else if (token.equals("/")) {
                    val = operands.pop() / val;
                }
                operands.push(val);
            }
        }
        return operands.pop();
    }

    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
}
