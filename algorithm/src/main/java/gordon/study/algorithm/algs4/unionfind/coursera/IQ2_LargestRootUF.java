/**
 * Union-find with specific canonical element. Add a method find() to the union-find data type so that find(i)
 * returns the largest element in the connected component containing ii. The operations, union(), connected(),
 * and find() should all take logarithmic time or better.
 *
 * For example, if one of the connected components is {1,2,6,9}, then the find() method should return 9 for
 * each of the four elements in the connected components.
 */
package gordon.study.algorithm.algs4.unionfind.coursera;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class IQ2_LargestRootUF {

    private int[] id;

    private int[] size;

    private int[] max;

    private int count;

    public IQ2_LargestRootUF(int totalSize) {
        this.count = totalSize;
        this.id = new int[totalSize];
        this.size = new int[totalSize];
        this.max = new int[totalSize];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            size[i] = 1;
            max[i] = i;
        }
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) {
            return;
        }
        if (size[pRoot] > size[qRoot]) {
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
            if (max[qRoot] > max[pRoot]) {
                max[pRoot] = max[qRoot];
            }
        } else {
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
            if (max[pRoot] > max[qRoot]) {
                max[qRoot] = max[pRoot];
            }
        }
        count--;
    }

    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    public int find(int p) {
        return max[root(p)];
    }

    public int count() {
        return count;
    }

    private int root(int p) {
        while (id[p] != p) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    // 仅用于测试代码正确性
    private boolean verify() {
        Map<Integer, Set<Integer>> group = new HashMap<>();
        for (int i = 0; i < id.length; i++) {
            int root = find(i);
            if (!group.containsKey(root)) {
                group.put(root, new HashSet<>());
            }
            group.get(root).add(i);
        }
        for (int root : group.keySet()) {
            int max = group.get(root).stream().max((i1, i2) -> i1.compareTo(i2)).get();
            if (root != max) {
                System.out.printf("root: %d, max: %d\n", root, max);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/unionfind/mediumUF.txt"));
        int n = scanner.nextInt();
        IQ2_LargestRootUF instance = new IQ2_LargestRootUF(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            instance.union(p, q);
        }
        System.out.println(instance.count() + " components");
        System.out.println(instance.verify());
        scanner.close();
    }
}
