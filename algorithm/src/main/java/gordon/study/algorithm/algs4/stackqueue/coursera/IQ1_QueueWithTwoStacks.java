/**
 * Queue with two stacks. Implement a queue with two stacks so that each queue operations takes a
 * constant amortized number of stack operations.
 */
package gordon.study.algorithm.algs4.stackqueue.coursera;

import edu.princeton.cs.algs4.Stack;

import java.util.NoSuchElementException;

public class IQ1_QueueWithTwoStacks<Item> {

    private Stack<Item> stack1 = new Stack<>();

    private Stack<Item> stack2 = new Stack<>();

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return stack1.size() + stack2.size();
    }

    public Item peek() {
        if (stack2.isEmpty()) {
            // pop all elements from stack1 to stack2
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return stack2.peek();
    }

    public void enqueue(Item item) {
        stack1.push(item);
    }

    public Item dequeue() {
        if (stack2.isEmpty()) {
            // pop all elements from stack1 to stack2
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return stack2.pop();
    }

    public static void main(String[] args) {
        IQ1_QueueWithTwoStacks queue = new IQ1_QueueWithTwoStacks();
        queue.enqueue("1");
        queue.enqueue("2");
        System.out.println(queue.dequeue()); // 1
        queue.enqueue("3");
        queue.enqueue("4");
        System.out.println(queue.dequeue()); // 2
        System.out.println(queue.dequeue()); // 3
    }
}
