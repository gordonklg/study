package gordon.study.algorithm.algs4.heap.practice;

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class MaxHeap {

    private int[] heap;

    private int size;

    public MaxHeap() {
        heap = new int[100];
    }

    public void heapify(int[] a) {
        size = a.length;
        System.arraycopy(a, 0, heap, 1, a.length);
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

    public void insert(int a) {
        heap[++size] = a;
        buttomUp(size);
    }

    public int getMax() {
        return heap[1];
    }

    public int popMax() {
        if (isEmpty()) throw new NoSuchElementException("Empty");
        int max = heap[1];
        heap[1] = heap[size--];
        topDown(1);
        // heap[size+1] = null;
        return max;
    }

    private void buttomUp(int k) {
        while (k > 1 && heap[k / 2] < heap[k]) {
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
            if (j < n && heap[j] < heap[j + 1]) {
                j++;
            }
            if (heap[k] >= heap[j]) return;
            swap(k, j);
            k = j;
        }
    }

    private void swap(int p, int q) {
        int temp = heap[p];
        heap[p] = heap[q];
        heap[q] = temp;
    }

    public int[] getArray() {
        return heap;
    }

    public void sort() {
        int pivot = size;
        while (pivot > 1) {
            swap(pivot--, 1);
            topDown(1, pivot);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};

        MaxHeap maxHeap = new MaxHeap();
        for (int i = 0; i < 20; i++) {
            maxHeap.insert(StdRandom.uniform(100));
        }
        for (int i = 0; i < 20; i++) {
            System.out.print(maxHeap.popMax() + " ");
        }
        System.out.println();

        maxHeap = new MaxHeap();
        maxHeap.heapify(arr);
        for (int i = 0; i < maxHeap.size(); i++) {
            System.out.print(maxHeap.getArray()[i + 1] + " ");
        }
        System.out.println();

        maxHeap.sort();
        for (int i = 0; i < maxHeap.size(); i++) {
            System.out.print(maxHeap.getArray()[i + 1] + " ");
        }
    }

}
