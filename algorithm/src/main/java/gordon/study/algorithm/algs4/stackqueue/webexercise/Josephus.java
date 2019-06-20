package gordon.study.algorithm.algs4.stackqueue.webexercise;

import gordon.study.algorithm.algs4.stackqueue.Queue;

public class Josephus {

    private static int TOTAL = 7;

    private static int INTERVAL = 2;

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();

        for (int i = 0; i < TOTAL; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < TOTAL; i++) {
            for (int j = 0; j < INTERVAL - 1; j++) {
                queue.enqueue(queue.dequeue());
            }
            System.out.print(queue.dequeue() + " ");
        }
    }
}
