package gordon.study.algorithm.algs4.stackqueue.coursera;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;

    private Item[] items;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == items.length) {
            Item[] newItems = (Item[]) new Object[2 * size];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int r = StdRandom.uniform(size);
        Item item = items[r];
        items[r] = items[size - 1];
        items[size - 1] = null; // avoid loitering
        size--;
        if (size <= items.length / 4 && size >= 2) {
            Item[] newItems = (Item[]) new Object[items.length / 2];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int r = StdRandom.uniform(size);
        return items[r];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private int i;

        private int[] seq;

        public RandomizedQueueIterator() {
            seq = new int[size];
            for (int j = 0; j < size; j++) {
                seq[j] = j;
            }
            StdRandom.shuffle(seq);
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (Item) items[seq[i++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> ss = new RandomizedQueue<>();
        for (int i = 0; i < 15; i++) {
            ss.enqueue("abc");
        }
        for (int i = 0; i < 15; i++) {
            ss.dequeue();
        }
        System.out.println("...");
    }
}
