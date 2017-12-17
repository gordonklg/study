package gordon.study.algorithm.leetcode.array;

import java.util.Arrays;

public class Leet621TaskScheduler {

    public static void main(String[] args) {
        Leet621TaskScheduler inst = new Leet621TaskScheduler();
        System.out.println(inst.leastInterval(new char[] { 'A', 'A', 'A', 'B', 'B', 'B', 'C' , 'C' }, 2));

    }

    int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        for (int i = 0; i < tasks.length; i++) {
            ++count[tasks[i] - 'A'];
        }
        Arrays.sort(count);
        int maxTaskSize = count[count.length - 1];
        int result = (maxTaskSize - 1) * (n + 1) + 1;
        for (int i = count.length - 2; count[i] == maxTaskSize; --i, ++result) {
        }
        if (result < tasks.length) {
            result = tasks.length;
        }
        return result;
    }
}
