/**
 * Successor with delete. Given a set of n integers S={0,1,...,n−1} and a sequence of requests of the following form:
 *
 * - Remove x from S
 * - Find the successor of x: the smallest y in S such that y≥x.
 * design a data type so that all operations (except construction) take logarithmic time or better in the worst case.
 */
package gordon.study.algorithm.algs4.unionfind.coursera;

public class IQ3_SuccessorWithDelete {

    private IQ2_LargestRootUF uf;

    private int size;

    public IQ3_SuccessorWithDelete(int size) {
        this.size = size;
        uf = new IQ2_LargestRootUF(size + 1);
    }

    public void remove(int pos) {
        if (pos >= size) {
            throw new IllegalArgumentException("index " + pos + " is not between 0 and " + (size - 1));
        }
        uf.union(pos, pos + 1);
    }

    public int getSuccessor(int pos) {
        if (pos >= size) {
            throw new IllegalArgumentException("index " + pos + " is not between 0 and " + (size - 1));
        }
        int successor = uf.find(pos);
        return successor < size ? successor : -1;
    }

    public static void main(String[] args) {
        IQ3_SuccessorWithDelete instance = new IQ3_SuccessorWithDelete(8);
        instance.remove(2);
        instance.remove(3);
        instance.remove(4);
        instance.remove(7);
        System.out.println("Successor of 1: " + instance.getSuccessor(1));
        System.out.println("Successor of 2: " + instance.getSuccessor(2));
        System.out.println("Successor of 4: " + instance.getSuccessor(4));
        System.out.println("Successor of 5: " + instance.getSuccessor(5));
        System.out.println("Successor of 7: " + instance.getSuccessor(7));
    }
}
