/**
 * Stack with max. Create a data structure that efficiently supports the stack operations (push and pop)
 * and also a return-the-maximum operation. Assume the elements are reals numbers so that you can compare them.
 */
package gordon.study.algorithm.algs4.stackqueue.coursera;

import edu.princeton.cs.algs4.Stack;

import java.util.NoSuchElementException;

public class IQ2_StackWithMax<Item extends Comparable> {

    private Stack<Item> stack = new Stack<>();

    private Stack<Item> maxStack = new Stack<>();

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void push(Item item) {
        stack.push(item);
        if (maxStack.isEmpty() || item.compareTo(maxStack.peek()) >= 0) {
            maxStack.push(item);
        }
    }

    public Item pop() {
        Item item = stack.pop();
        if (!maxStack.isEmpty() && item.compareTo(maxStack.peek()) == 0) {
            maxStack.pop();
        }
        return item;
    }

    public Item peek() {
        return stack.peek();
    }

    public Item getMaximum() {
        if (maxStack.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return maxStack.peek();
    }

    public static void main(String[] args) {
        IQ2_StackWithMax queue = new IQ2_StackWithMax();
        queue.push("3");
        System.out.println(queue.getMaximum()); // 3
        queue.push("9");
        System.out.println(queue.getMaximum()); // 9
        queue.push("6");
        System.out.println(queue.getMaximum()); // 9
        queue.push("9");
        queue.push("5");
        queue.pop();
        queue.pop();
        System.out.println(queue.getMaximum()); // 9
        queue.pop();
        queue.pop();
        System.out.println(queue.getMaximum()); // 3
    }
}
