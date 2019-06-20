/**
 * Selection in two sorted arrays. Given two sorted arrays a[] and b[], of sizes n1 and n2, respectively,
 * design an algorithm to find the kth largest key. The order of growth of the worst case running time of
 * your algorithm should be logn, where n = n1 + n2.
 */
package gordon.study.algorithm.algs4.quicksort.coursera;

public class IQ2_SelectionInTwoSortedArrays {

}
/*
Hint: there are two basic approaches.

Approach A: Compute the median in a[] and the median in b[]. Recur in a subproblem of roughly half the size.
Approach B: Design a constant-time algorithm to determine whether a[i] is the kth largest key. Use this subroutine and binary search.
Dealing with corner cases can be tricky.
 */