/*
ID: vhli2020
PROG: kimbits
LANG: JAVA
 */

package section2;

import java.io.*;
import java.util.*;

public class kimbits {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("kimbits.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        long I = Long.parseLong(st.nextToken());
        br.close();

        int[][] size = new int[N+1][L+1];
        for (int l = 1; l < L+1; l++) {
            size[1][l] = 2;
        }
        size[1][0] = 1;

        for (int n = 2; n < N+1; n++) {
            for (int l = 0; l < L+1; l++) {
                if (l == 0) {
                    size[n][l] = 1;
                } else {
                    size[n][l] = size[n - 1][l - 1] + size[n - 1][l];
                }
            }
        }

        for (int n = 0; n < N+1; n++) {
            System.out.print("n = " + n + "   ");
            for (int l = 0; l < L+1; l++) {
                System.out.print(size[n][l] + " ");
            }
            System.out.println();
        }

        String ans = findString(size, I, N, L);
        System.out.println();
        System.out.println(ans);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
        pw.println(ans);
        pw.close();

    }

    // i - ith number; n = num bits; l = max num of 1's
    //
    public static String findString(int[][] size, long i, int n, int l) {
        if (n == 1) {
            assert i == 0 || i == 1;
            if (i == 1) {
                return "0";
            } else {
                return "1";
            }

        }

        if (i > size[n-1][l]) {
            return "1" + findString(size, i - size[n-1][l], n-1, l-1);
        } else {
            return "0" + findString(size, i, n-1, l);
        }
    }
}
