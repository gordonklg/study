/**
 * Social network connectivity. Given a social network containing n members and a log file containing m timestamps
 * at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which
 * all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that
 * the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your
 * algorithm should be mlogn or better and use extra space proportional to n.
 */
package gordon.study.algorithm.algs4.unionfind.coursera;

import edu.princeton.cs.algs4.UF;

import java.io.InputStream;
import java.util.Scanner;

public class IQ1_SocialNetwork {

    public String getFullConnTime() {
        String result = "NOT FULL CONNECTED";
        InputStream input = ClassLoader.getSystemResourceAsStream("algs4/unionfind/socialnetwork.txt");
        Scanner scanner = new Scanner(input);
        int n = scanner.nextInt();
        UF uf = new UF(n);
        while (scanner.hasNextLine()) {
            uf.union(scanner.nextInt(), scanner.nextInt());
            String date = scanner.nextLine();
            if (uf.count() == 1) {
                result = date.trim();
                break;
            }
        }
        scanner.close();
        return result;
    }

    public static void main(String[] args) {
        IQ1_SocialNetwork instance = new IQ1_SocialNetwork();
        System.out.println(instance.getFullConnTime());
    }

}
