package gordon.study.algorithm.algs4.searchtree.practice;

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class BinarySearchTreeRecursive<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public BinarySearchTreeRecursive() {
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        root = put(root, key, val);
    }

    // 将 key 放入指定节点构成的子树（指定节点可能为null）
    // key should not be null!
    private Node put(Node node, Key key, Value val) {
        if (node == null) {
            return new Node(key, val);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) { // 放入左子树
            node.left = put(node.left, key, val);
        } else if (cmp > 0) { // 放入右子树
            node.right = put(node.right, key, val);
        } else { // 覆盖旧值
            node.val = val;
        }
        return node;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node node = get(root, key);
        return node == null ? null : node.val;
    }

    // key should not be null!
    private Node get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.right == null) {
                return node.left;
            }
            Node pivot = node;
            node = min(pivot.right);
            node.right = deleteMin(pivot.right);
            node.left = pivot.left;
        }
        return node;
    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(root, key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return size(node.left) + size(node.right) + 1;
    }

    public Key min() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        return min(root).key;
    }

    // node should not be null!
    private Node min(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return min(node.left);
        }
    }

    public Key max() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        return max(root).key;
    }

    // node should not be null!
    private Node max(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return max(node.right);
        }
    }

    public void deleteMin() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        deleteMin(root);
    }

    // node should not be null!
    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    public void deleteMax() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        deleteMax(root);
    }

    // node should not be null!
    private Node deleteMax(Node node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        return node;
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException();
        }
        return select(root, k).key;
    }

    private Node select(Node node, int k) {
        if (node == null) {
            return null;
        }
        int lc = size(node.left);
        if (k < lc) {
            return select(node.left, k);
        } else if (k > lc) {
            return select(node.right, k - lc - 1);
        } else {
            return node;
        }
    }

    // 小于key的键的数量
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return rank(root, key);
    }

    public int rank(Node node, Key key) {
        if (node == null) {
            return 0;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return rank(node.left, key);
        } else if (cmp > 0) {
            return size(node.left) + 1 + rank(node.right, key);
        } else {
            return size(node.left);
        }
    }

    private void print() {
        print(root);
        System.out.println();
    }

    private void print(Node node) {
        if (node == null) {
            return;
        }
        print(node.left);
        System.out.print(node.key + " ");
        print(node.right);
    }

    public static void main(String[] args) {
        BinarySearchTreeRecursive<Integer, String> bst = new BinarySearchTreeRecursive<>();
        bst.put(50, "50");
        for (int i = 0; i < 20; i++) {
            int key = StdRandom.uniform(100);
            System.out.print(key + " ");
            bst.put(key, "" + key);
        }
        System.out.println();

        bst.print();

        System.out.print(bst.size() + " size # ");
        System.out.print(bst.get(50) + " # ");
        System.out.println(bst.get(-1));

        bst.delete(50);
        bst.delete(-1);
        System.out.print(bst.size() + " size # ");
        bst.print();

        System.out.print(bst.min() + " min # ");
        bst.deleteMin();
        System.out.print(bst.size() + " size # ");
        bst.print();

        System.out.print(bst.max() + " max # ");
        bst.deleteMax();
        System.out.print(bst.size() + " size # ");
        bst.print();

        System.out.print(bst.select(0) + " should be first item # ");
        System.out.print(bst.select(5) + " should be bst[5] # ");
        System.out.println(bst.select(bst.size() - 1) + " should be last item");

        System.out.print(bst.rank(0) + " should be 0 # ");
        System.out.print(bst.rank(100) + " should be bst size # ");
        System.out.print(bst.rank(bst.select(5)) + " should be 5 # "); // i == rank(select(i))
        System.out.println(bst.select(bst.rank(bst.max())) + " should be last item"); // key = select(rank(key))

    }
}
