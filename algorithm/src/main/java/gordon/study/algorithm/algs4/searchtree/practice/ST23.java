package gordon.study.algorithm.algs4.searchtree.practice;

import edu.princeton.cs.algs4.StdRandom;

public class ST23<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node<Key extends Comparable<Key>, Value> {
        private DataNode<Key, Value>[] items;
        private Node[] children;
        private int itemCount;

        public Node(Key key, Value value) {
            this.items = new DataNode[2];
            this.items[0] = new DataNode(key, value);
            this.children = new Node[3];
            this.itemCount = 1;
        }

//        public Node(DataNode dataNode) {
//            DataNode<Key, Value>[] dataNodes = new DataNode[3];
//            dataNodes[0] = dataNode;
//            this.items = dataNodes;
//            this.itemCount = 1;
//        }

//        public Node23(DataNode[] dataNodes, int size) {
//            this.items = dataNodes;
//            this.size = size;
//        }

        public boolean isLeaf() {
            return children[0] == null;
        }

        public void insertDataNode(Key key, Value value) {
            items[itemCount++] = new DataNode(key, value);
        }

        public void displayNode(StringBuilder sb) {
            sb.append("[");
            for (int i = 0; i < itemCount; i++) {
                sb.append(items[i].key);
                if (i < itemCount - 1) {
                    sb.append(" ");
                }
            }
            sb.append("]");
        }
    }

    private class DataNode<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value val;

        public DataNode(Key key, Value value) {
            this.key = key;
            this.val = value;
        }
    }

    public ST23() {
    }


    //    public int size() {
//        return size(root);
//    }
//
//    private int size(Node node) {
//        if (node == null) {
//            return 0;
//        } else {
//            return node.size;
//        }
//    }
//
//    public boolean isEmpty() {
//        return size() == 0;
//    }
//
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) {
            return new Node(key, val);
        }
//        if (node.itemCount >= 3) {
//            return node;
//        }
//        for (int i = 0; i < node.itemCount; i++) {
//            int cmp = key.compareTo((Key) node.items[i].key);
//            if (cmp < 0) {
//                put(node.children[i], key, val);
//                break;
//            } else if (cmp == 0) {
//                node.items[i].val = val;
//                break;
//            } else if (cmp > 0 && i == node.itemCount - 1) {
//                put(node.children[i + 1], key, val);
//                break;
//            }
//        }


        if (node.itemCount == 1) { // a leaf 2-node
            int cmp = key.compareTo((Key) node.items[0].key);
            if (cmp < 0) {
                node.items[1] = node.items[0];
                node.items[0] = new DataNode(key, val);
                node.itemCount = 2;
            } else if (cmp > 0) {
                node.items[1] = new DataNode(key, val);
                node.itemCount = 2;
            } else {
                node.items[0].val = val;
            }
        } else {
            for (int i = 0; i < node.itemCount; i++) {
                int cmp = key.compareTo((Key) node.items[i].key);
                if (cmp < 0) {
                    node.children[i] = put(node.children[i], key, val);
                    break;
                } else if (cmp == 0) {
                    node.items[i].val = val;
                    break;
                } else if (cmp > 0 && i == node.itemCount - 1) {
                    node.children[i + 1] = put(node.children[i + 1], key, val);
                    break;
                }
            }
        }

//        if (node.itemCount == 3) {
//            Node parent = new Node(node.items[1]);
//            Node left = new Node(node.items[0]);
//            Node right = new Node(node.items[2]);
//            parent.children[0] = left;
//            parent.children[1] = right;
//            return parent;
//        }
//        if (node.itemCount == 1) {
//            int cmp = key.compareTo((Key) node.items[0].key);
//            if (cmp == 0) {
//                node.items[0].val = value;
//            } else {
//                if (node.isLeaf()) {
//                    node.insertDataNode(key, value);
//                } else if (cmp < 0) {
//                    node.children[0] = put(node.children[0], key, value);
//                } else {
//                    node.children[1] = put(node.children[1], key, value);
//                }
//            }
//        }
//        int cmp = key.compareTo(node.key);
//        if (cmp < 0) {
//            node.left = put(node.left, key, value);
//        } else if (cmp > 0) {
//            node.right = put(node.right, key, value);
//        } else {
//            node.val = value;
//        }
//        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    private void insertDataNode(Node node, int pos, Key key, Value value) {
        for (int i = node.itemCount; i > pos; i--) {
            node.items[i] = node.items[i - 1];
        }
        node.items[pos] = new DataNode(key, value);
        node.itemCount++;
    }

    public void displayTree() {
        StringBuilder sb = new StringBuilder();
        fineDisplay(sb, root, 0, 0);
        System.out.println(sb.toString());
    }

    // 展示23树的结构
    private void fineDisplay(StringBuilder sb, Node node, int level, int childNum) {
        sb.append("(").append(level).append(")");
        node.displayNode(sb);
        for (int i = 0; i < node.itemCount + 1; i++) {
            Node child = node.children[i];
            if (child != null) {
                sb.append("\n");
                fineDisplay(sb, child, level + 1, i);
            } else {
                return;
            }
        }

    }

    //
//    public Value get(Key key) {
//        return get(root, key);
//    }
//
//    private Value get(Node node, Key key) {
//        if (node == null) {
//            return null;
//        }
//        int cmp = key.compareTo(node.key);
//        if (cmp == 0) {
//            return node.val;
//        } else if (cmp < 0) {
//            return get(node.left, key);
//        } else {
//            return get(node.right, key);
//        }
//    }
//
//    public boolean contains(Key key) {
//        return get(key) != null;
//    }
//
//    public void delete(Key key) {
//        root = delete(root, key);
//    }
//
//    private Node delete(Node node, Key key) {
//        int cmp = key.compareTo(node.key);
//        if (cmp > 0) {
//            delete(node.right, key);
//        } else if (cmp < 0) {
//            delete(node.left, key);
//        } else {
//            if (node.left == null) {
//                return node.right;
//            }
//            if (node.right == null) {
//                return node.left;
//            }
//            Node p = node;
//            node = min(node.right);
//            node.right = deleteMin(node.right);
//            node.left = p.left;
//        }
//        return node;
//    }
//
//    public Key min() {
//        if (isEmpty()) {
//            throw new NoSuchElementException();
//        }
//        return min(root).key;
//    }
//
//    // node should not be null
//    private Node min(Node node) {
//        if (node.left == null) {
//            return node;
//        } else {
//            return min(node.left);
//        }
//    }
//
//    public void deleteMin() {
//        if (isEmpty()) {
//            throw new NoSuchElementException();
//        }
//        root = deleteMin(root);
//    }
//
//    // 返回 node 子树删除掉最小节点后 新子树的根节点（可能不再是 node）
//    // node should not be null
//    private Node deleteMin(Node node) {
//        if (node.left == null) {
//            return node.right;
//        }
//        node.left = deleteMin(node.left);
//        node.size = size(node.left) + size(node.right) + 1;
//        return node;
//    }
//
//    public void deleteMax() {
//        if (isEmpty()) {
//            throw new NoSuchElementException();
//        }
//        root = deleteMax(root);
//    }
//
//    // node should not be null
//    private Node deleteMax(Node node) {
//        if (node.right == null) {
//            return node.left;
//        }
//        node.right = deleteMax(node.right);
//        node.size = size(node.left) + size(node.right) + 1;
//        return node;
//    }
//
//    public Key max() {
//        if (isEmpty()) {
//            throw new NoSuchElementException();
//        }
//        return max(root).key;
//    }
//
//    // node should not be null
//    private Node max(Node node) {
//        if (node.right == null) {
//            return node;
//        } else {
//            return max(node.right);
//        }
//    }
//
//    public Key select(int k) {
//        if (k > size()) {
//            throw new IllegalArgumentException();
//        }
//        return select(root, k);
//    }
//
//    private Key select(Node node, int k) {
//        if (node == null) {
//            return null;
//        }
//        int s = size(node.left);
//        if (s == k) {
//            return node.key;
//        } else if (s > k) {
//            return select(node.left, k);
//        } else {
//            return select(node.right, k - s - 1);
//        }
//    }
//
//    public int rank(Key key) {
//        return  rank(root, key);
//    }
//
//    private int rank(Node node, Key key) {
//        if (node == null) {
//            return 0;
//        }
//        int cmp = key.compareTo(node.key);
//        if (cmp < 0) {
//            return rank(node.left, key);
//        } else if (cmp > 0) {
//            return rank(node.right, key) + size(node.left) + 1;
//        } else {
//            return 1;
//        }
//    }
//
//    public void print() {
//        System.out.print("BST size: " + size() + " [");
//        print(root);
//        System.out.println("]");
//    }
//
//    private void print(Node node) {
//        if (node == null) {
//            return;
//        }
//        print(node.left);
//        System.out.print(node.key + " ");
//        print(node.right);
//    }
//
    public static void main(String[] args) {
        ST23 bst = new ST23();
//        bst.put(0, "0");
        for (int i = 0; i < 10; i++) {
            int key = StdRandom.uniform(100);
            System.out.print(key + " ");
            bst.put(key, "" + key);
        }
        System.out.println();
        bst.displayTree();
//
//        bst.print();
//
//        System.out.println(bst.get(0));
//        System.out.println(bst.get(-1));
//
//        bst.delete(0);
//        bst.print();
//
//        bst.deleteMin();
//        bst.print();
//
//        bst.deleteMax();
//        bst.print();
//
//        System.out.println(bst.select(0) + " should be first item");
//        System.out.println(bst.select(5) + " should be 5th item");
//        System.out.println(bst.select(bst.size() - 1) + " should be last item");
//
//        System.out.println(bst.rank(0) + " should be 0");
//        System.out.println(bst.rank(100) + " should be bst size");
//        System.out.println(bst.rank(50));
//        System.out.println(bst.rank(bst.min()) + " should be 1");
//
////        for (String s : bst.levelOrder())
////            StdOut.println(s + " " + bst.get(s));
////
////        StdOut.println();
////
////        for (String s : bst.keys())
////            StdOut.println(s + " " + bst.get(s));
    }
}
