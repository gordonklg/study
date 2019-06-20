package gordon.study.algorithm.algs4.unionfind.practice;

import gordon.study.algorithm.algs4.unionfind.UF;

import java.util.Scanner;

public class UFTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/unionfind/mediumUF.txt"));
        int n = scanner.nextInt();
        UF instance = new UF(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            instance.union(p, q);
        }
        System.out.println(instance.count() + " components");
        scanner.close();
    }
}
