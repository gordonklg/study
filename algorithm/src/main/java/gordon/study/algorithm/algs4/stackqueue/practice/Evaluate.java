package gordon.study.algorithm.algs4.stackqueue.practice;

import gordon.study.algorithm.algs4.stackqueue.Stack;
import gordon.study.algorithm.algs4.unionfind.practice.QuickFind;

import java.util.Scanner;

public class Evaluate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/stackqueue/evaluate.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.printf("%s = %.2f\n", line, evaluate(line));
        }
        scanner.close();
    }

    private static double evaluate(String exp) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    operands.push(Double.parseDouble(number.toString()));
                    number.setLength(0);
                }
                if (isOperator(c)) {
                    operators.push(c);
                } else if (c == ')') {
                    char operator = operators.pop();
                    double val = operands.pop();
                    if(operator == '+') {
                        val = operands.pop() + val;
                    } else if(operator == '-') {
                        val = operands.pop() - val;
                    } else if(operator == '*') {
                        val = operands.pop() * val;
                    } else if(operator == '/') {
                        val = operands.pop() / val;
                    }
                    operands.push(val);
                }
            }
        }
        return operands.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
