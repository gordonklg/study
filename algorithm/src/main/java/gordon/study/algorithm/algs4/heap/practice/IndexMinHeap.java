package gordon.study.algorithm.algs4.heap.practice;

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class IndexMinHeap {

    private int[] elements;

    private int[] index;

    private int size;

    public IndexMinHeap() {
        elements = new int[100];
        index = new int[100];
    }

    public void heapify(int[] a) {
        size = a.length;
        System.arraycopy(a, 0, elements, 1, a.length);
        for (int i = 1; i <= a.length; i++) {
            index[i] = i;
        }
        for (int i = size / 2; i > 0; i--) {
            topDown(i);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // 指定（elements 数组）位置插入新值（一般在 popMin 后使用）
    public void insert(int pos, int a) {
        elements[pos] = a;
        index[++size] = pos;
        buttomUp(size);
    }

    public int getMin() {
        return index[1];
    }

    public int getMinValue() {
        return elements[index[1]];
    }

    // 弹出最小值（在 elements 数组中）的下标
    public int popMin() {
        if (isEmpty()) throw new NoSuchElementException("Empty");
        int min = index[1];
        swap(1, size--);
        topDown(1);
        // elements[size+1] = null;
        return min;
    }

    private void buttomUp(int k) {
        while (k > 1 && elements[index[k / 2]] > elements[index[k]]) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    private void topDown(int k) {
        topDown(k, size);
    }

    private void topDown(int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && elements[index[j + 1]] < elements[index[j]]) {
                j++;
            }
            if (elements[index[k]] <= elements[index[j]]) return;
            swap(k, j);
            k = j;
        }
    }

    private void swap(int p, int q) {
        int temp = index[p];
        index[p] = index[q];
        index[q] = temp;
    }

    private int[] getArray() {
        return elements;
    }

    private int[] getIndexArray() {
        return index;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};

//        debug();

        IndexMinHeap minHeap = new IndexMinHeap();
        for (int i = 0; i < 10; i++) {
            minHeap.insert(i, StdRandom.uniform(100));
        }
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.getArray()[minHeap.popMin()] + " ");
        }
        System.out.println();

        minHeap = new IndexMinHeap();
        minHeap.heapify(arr);
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.getArray()[minHeap.popMin()] + " ");
        }
        System.out.println();
    }

    private static void debug() {
        IndexMinHeap minHeap = new IndexMinHeap();
        for (int i = 0; i < 10; i++) {
            minHeap.insert(i, StdRandom.uniform(100));
        }

        printArray(minHeap.getArray(), 1, 10);
        printArray(minHeap.getIndexArray(), 1, minHeap.size());

        System.out.println(minHeap.popMin() + " ");
        printArray(minHeap.getArray(), 1, 10);
        printArray(minHeap.getIndexArray(), 1, minHeap.size());

        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.popMin() + " ");
        }
        System.out.println();
    }

    private static void printArray(int[] a, int begin, int total) {
        for (int i = begin; i < total + begin; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
