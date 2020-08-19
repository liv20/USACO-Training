/*
ID: vhli2020
PROG: rockers
LANG: JAVA
 */

package section4;

import java.util.*;
import java.io.*;

public class rockers {
    static int N;
    static int T;
    static int M;
    static int[] songs;

    public static void main(String[] args) throws Exception  {
        BufferedReader br = new BufferedReader(new FileReader("rockers.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N - number of songs, T - time, M - number of compact disks
        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        songs = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            songs[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // dp[song number you're on][compact disk number][time]
        // at dp, n songs have already been used (on song number n bc starting on 0)
        // m disks have already been used (on compact disk number m)
        // and t time has already been used on the m'th compact disk (0 to T)
        int[][][] dp = new int[N+1][M][T+1]; // at most ~80,000
        // choices with a new song: put on the same disk (check for time), put on next disk,
        // or skip

        int max = 0;
        for (int m = 0; m < M; m++) {
            for (int t = 0; t <= T; t++) {
                for (int n = 0; n < N; n++) {
                    // same disk
                    // dp[n+1][m][t+songs[n]] - after put onto the same disk
                    // dp[n][m][t] + 1
                    if (t+songs[n] <= T && dp[n][m][t] + 1 > dp[n+1][m][t+songs[n]]) {
                        dp[n+1][m][t+songs[n]] = dp[n][m][t] + 1;
                        max = Math.max(dp[n+1][m][t+songs[n]], max);
                    }
                    // put on next disk
                    // dp[n+1][m+1][songs[n]] - the next disk
                    if (m+1 < M && songs[n] <= T && dp[n][m][t] + 1 > dp[n+1][m+1][songs[n]]) {
                        dp[n+1][m+1][songs[n]] = dp[n][m][t] + 1;
                        max = Math.max(max, dp[n+1][m+1][songs[n]]);
                    }
                    // skip song
                    // dp[n+1][m][t]
                    if (dp[n][m][t] > dp[n+1][m][t]) {
                        dp[n+1][m][t] = dp[n][m][t];
                    }
                }
            }
        }

        System.out.println(max);



        for (int n = 0; n < N; n++) {
            for (int m = 0; m < M; m++) {
                for (int t = 0; t <= T; t++) {
                    System.out.print(dp[n][m][t] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rockers.out")));
        pw.println(max);
        pw.close();
    }

}
