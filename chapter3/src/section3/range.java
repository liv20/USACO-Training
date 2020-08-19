/*
ID: vhli2020
PROG: range
LANG: JAVA
 */

package section3;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class range {
    static int N;
    static int[][] a;
    static int[] ans;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("range.in"));
        N = Integer.parseInt(br.readLine());

        a = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            String line = br.readLine();
            for (int j = 1; j <= N; j++) {
                a[i][j] = Integer.parseInt(line.substring(j-1, j));
            }
        }
        br.close();

        /*for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }*/
        ans = new int[N+1];

        for (int i = N-1; i >= 1; i--) {
            for (int j = N-1; j >= 1; j--) {
                int k = 0;
                int left = 0;
                int right = 0;
                while (i+k <= N && a[i+k][j] != 0) {
                    k++;
                }
                left = k;
                k = 0;
                while (j+k <= N && a[i][j+k] != 0) {
                    k++;
                }
                right = k;

                a[i][j] = Math.min(left, right);
                a[i][j] = Math.min(a[i][j], 1 + a[i+1][j+1]);
                ans[a[i][j]]++;
            }
        }

        for (int i = N-1; i >= 2; i--) {
            ans[i] += ans[i+1];
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
        for (int i = 2; i <= N; i++) {
            if (ans[i] > 0) {
                pw.println(i + " " + ans[i]);
            }
        }
        pw.close();


    }


}
