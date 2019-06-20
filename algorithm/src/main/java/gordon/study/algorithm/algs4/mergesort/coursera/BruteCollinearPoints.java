package gordon.study.algorithm.algs4.mergesort.coursera;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private int numOfSeg;

    private List<LineSegment> segmemts;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if(points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if(points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(points);

        segmemts = new ArrayList<>();

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                double slopepq = points[p].slopeTo(points[q]);
                if(slopepq == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                for (int r =  q + 1; r < points.length; r++) {
                    double slopepr = points[p].slopeTo(points[r]);
                    if(slopepr == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException();
                    }
                    if(Double.compare(slopepq, slopepr) != 0) {
                        continue;
                    }
                    for (int s = r + 1; s < points.length; s++) {
                        double slopeps = points[p].slopeTo(points[s]);
                        if(slopeps == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException();
                        }
                        if(Double.compare(slopepq, slopeps) == 0) {
                            segmemts.add(new LineSegment(points[p], points[s]));
                            numOfSeg++;
                        }
                    }
                }
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}