package gordon.study.algorithm.algs4.stackqueue.coursera;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;

    private Node<Item> last;

    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public Deque() {
    }

    public boolean isEmpty() {
        return n == 0; // or first == null or last == null
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> node = new Node<>();
        node.item = item;
        node.prev = null;
        node.next = first;
        if (first != null) {
            first.prev = node;
        } else {
            last = node;
        }
        first = node;
        n++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> node = new Node<>();
        node.item = item;
        node.prev = last;
        node.next = null;
        if (last != null) {
            last.next = node;
        } else {
            first = node;
        }
        last = node;
        n++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Node<Item> node = first;
        first = node.next;
        if (node.next != null) {
            node.next.prev = null;
        } else {
            last = null;
        }
        n--;
        return node.item;
    }

    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Node<Item> node = last;
        last = node.prev;
        if (node.prev != null) {
            node.prev.next = null;
        } else {
            first = null;
        }
        n--;
        return node.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {

    }
}
