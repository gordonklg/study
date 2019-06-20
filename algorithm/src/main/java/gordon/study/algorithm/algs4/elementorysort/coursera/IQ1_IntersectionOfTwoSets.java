/**
 * Intersection of two sets. Given two arrays a[] and b[], each containing nn distinct 2D points in the plane, design a
 * subquadratic algorithm to count the number of points that are contained both in array a[] and array b[].
 */
package gordon.study.algorithm.algs4.elementorysort.coursera;

import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.Stack;
import gordon.study.algorithm.algs4.elementorysort.practice.ShellSort;

import java.util.NoSuchElementException;

public class IQ1_IntersectionOfTwoSets {

    private class Point implements Comparable<Point> {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point p) {
            return x == p.x ? x - p.x : y - p.y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) {
        IQ1_IntersectionOfTwoSets inst = new IQ1_IntersectionOfTwoSets();
        inst.go();
    }

    private void go() {
        Point[] a = new Point[10];
        Point[] b = new Point[10];
        for (int i = 0; i < 10; i++) {
            a[i] = new Point(i, i);
            b[i] = new Point(i + 8, i + 8);
        }
        Shell.sort(a);
        Shell.sort(b);

        LinkedQueue<Point> queue = new LinkedQueue<>();
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) < 0) {
                i++;
            } else if (a[i].compareTo(b[j]) > 0) {
                j++;
            } else {
                queue.enqueue(a[i]);
                i++;
                j++;
            }
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }
}
