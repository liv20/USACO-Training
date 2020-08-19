/*
ID: vhli2020
PROG: fence9
LANG: JAVA
 */

package section4;

import java.util.*;
import java.io.*;

public class fence9 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("fence9.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        // use Pick's: A = I + B/2 - 1
        double A = (double) p * m / 2;
        double B = 0;
        // base
        B += (p+1);
        System.out.println("Base " + (p+1));
        // right side
        B += (1 + gcd(n-p, m));
        System.out.println("Right side " + (1 + gcd(n-p, m)));

        // left side
        B += (1 + gcd(n, m));
        B -= 3;
        System.out.println("Left side " + (1 + gcd(n, m)));
        System.out.println("B " + B);
        System.out.println("A " + A);

        long I = Math.round(A + 1 - B/2);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fence9.out")));
        pw.println(I);
        pw.close();
    }

    public static int gcd(int a, int b) {
        return recurse(Math.abs(a), Math.abs(b));
    }

    public static int recurse(int a, int b) {
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        }

        if (a > b) {
            return gcd(a % b, b);
        } else {
            return gcd(a, b % a);
        }
    }

}
