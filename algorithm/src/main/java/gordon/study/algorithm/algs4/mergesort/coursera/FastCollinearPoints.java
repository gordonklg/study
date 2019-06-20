package gordon.study.algorithm.algs4.mergesort.coursera;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private int numOfSeg;

    private List<LineSegment> segmemts;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        segmemts = new ArrayList<>();
        Point[] slopeOrder = new Point[points.length];
        System.arraycopy(points, 0, slopeOrder, 0, points.length);

        for (int p = 0; p < points.length; p++) {
            Point cur = points[p];
            Arrays.sort(slopeOrder, cur.slopeOrder());

            double last = Double.NEGATIVE_INFINITY;
            int pivot = 0;
            int i = 1;
            for (i = 1; i < slopeOrder.length; i++) {
                double slope = cur.slopeTo(slopeOrder[i]);
                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                if (slope != last) {
                    if (i - pivot >= 3) {
                        calcSegment(slopeOrder, pivot, i, cur);
                    }
                    last = slope;
                    pivot = i;
                }
            }
            if (i - pivot >= 3) {
                calcSegment(slopeOrder, pivot, i, cur);
            }
        }
    }

    private void calcSegment(Point[] slopeOrder, int left, int right, Point curPoint) {
        Point leftPoint = curPoint;
        Point rightPoint = curPoint;
        for (int i = left; i < right; i++) {
            if (slopeOrder[i].compareTo(leftPoint) < 0) {
                return; // if cur point is not the left point(start point) of the line, leave it to latter cycle.
            }
            if (slopeOrder[i].compareTo(rightPoint) > 0) {
                rightPoint = slopeOrder[i];
            }
        }
        segmemts.add(new LineSegment(leftPoint, rightPoint));
        numOfSeg++;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        return segmemts.toArray(new LineSegment[numOfSeg]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(ClassLoader.getSystemResource("algs4/mergesort/collinear/input8.txt"));
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
