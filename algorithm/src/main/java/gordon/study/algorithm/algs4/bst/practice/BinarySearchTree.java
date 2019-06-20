package gordon.study.algorithm.algs4.bst.practice;

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BinarySearchTree() {
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.val = value;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.val;
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    /**
     The main defect of Hibbard deletion is that it unbalances the tree, leading to sqrt n height.

     If instead of replacing the node to delete with its successor, you flip a coin and replace it with either its
     successor or predecessor, then, in practice, the height becomes logarithmic (but nobody has been able to prove
     this fact mathematically).
     */
    private Node delete(Node node, Key key) {
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            delete(node.right, key);
        } else if (cmp < 0) {
            delete(node.left, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node p = node;
            node = min(node.right);
            node.right = deleteMin(node.right);
            node.left = p.left;
        }
        return node;
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return min(root).key;
    }

    // node should not be null
    private Node min(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return min(node.left);
        }
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        root = deleteMin(root);
    }

    // 返回 node 子树删除掉最小节点后 新子树的根节点（可能不再是 node）
    // node should not be null
    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        root = deleteMax(root);
    }

    // node should not be null
    private Node deleteMax(Node node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return max(root).key;
    }

    // node should not be null
    private Node max(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return max(node.right);
        }
    }

    public Key select(int k) {
        if (k > size()) {
            throw new IllegalArgumentException();
        }
        return select(root, k);
    }

    private Key select(Node node, int k) {
        if (node == null) {
            return null;
        }
        int s = size(node.left);
        if (s == k) {
            return node.key;
        } else if (s > k) {
            return select(node.left, k);
        } else {
            return select(node.right, k - s - 1);
        }
    }

    public int rank(Key key) {
        return  rank(root, key);
    }

    private int rank(Node node, Key key) {
        if (node == null) {
            return 0;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return rank(node.left, key);
        } else if (cmp > 0) {
            return rank(node.right, key) + size(node.left) + 1;
        } else {
            return 1;
        }
    }

    public void print() {
        System.out.print("BST size: " + size() + " [");
        print(root);
        System.out.println("]");
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
        BinarySearchTree bst = new BinarySearchTree();
        bst.put(0, "0");
        for (int i = 0; i < 20; i++) {
            int key = StdRandom.uniform(100);
            System.out.print(key + " ");
            bst.put(key, "" + key);
        }
        System.out.println();

        bst.print();

        System.out.println(bst.get(0));
        System.out.println(bst.get(-1));

        bst.delete(0);
        bst.print();

        bst.deleteMin();
        bst.print();

        bst.deleteMax();
        bst.print();

        System.out.println(bst.select(0) + " should be first item");
        System.out.println(bst.select(5) + " should be 5th item");
        System.out.println(bst.select(bst.size() - 1) + " should be last item");

        System.out.println(bst.rank(0) + " should be 0");
        System.out.println(bst.rank(100) + " should be bst size");
        System.out.println(bst.rank(50));
        System.out.println(bst.rank(bst.min()) + " should be 1");

//        for (String s : bst.levelOrder())
//            StdOut.println(s + " " + bst.get(s));
//
//        StdOut.println();
//
//        for (String s : bst.keys())
//            StdOut.println(s + " " + bst.get(s));
    }
}
