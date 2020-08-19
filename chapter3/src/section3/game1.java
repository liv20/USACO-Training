/*
ID: vhli2020
PROG: game1
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

public class game1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("game1.in"));
        int N = Integer.parseInt(br.readLine());
        int[] b = new int[N+1];

        int i = 1;
        while (i <= N) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                b[i] = Integer.parseInt(st.nextToken());
                i++;
            }
        }
        br.close();

        // t[length of string][starting number index][player 0 vs player 1]
        int[][][] t = new int[N+1][N+1][2];
        for(int n = 1; n <= N; n++) {
            t[1][n][0] = b[n];
        }

        for (int l = 2; l <= N; l++) {
            for (int ind = 1; ind + l <= N+1; ind++) {
                int takeRight = b[ind+l-1] + t[l-1][ind][1];//t[ind][l-1][1];
                int takeLeft = b[ind] + t[l-1][ind+1][1];//t[ind+1][l-1][1];
                if (takeRight > takeLeft) {
                    t[l][ind][0] = takeRight;
                    t[l][ind][1] = t[l-1][ind][0];//t[ind][l-1][0];
                } else {
                    t[l][ind][0] = takeLeft;
                    t[l][ind][1] = t[l-1][ind+1][0];//t[ind+1][l-1][0];
                }

            }
        }

        for (int l = 1; l <= N; l++) {
            for (int ind = 1; ind <= N+1-l; ind++) {
                System.out.print(t[l][ind][0] + " " + t[l][ind][1] + "     ");
            }
            System.out.println();
        }

        System.out.println(t[N][1][0]);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("game1.out")));
        pw.println(t[N][1][0] + " " + t[N][1][1]);
        pw.close();

    }
}
