package gordon.study.algorithm.algs4.unionfind.coursera;

import java.io.InputStream;
import java.util.Scanner;

import edu.princeton.cs.algs4.UF;

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
