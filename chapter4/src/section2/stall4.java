/*
ID: vhli2020
PROG: stall4
LANG: JAVA
 */

package section2;

import java.io.*;
import java.util.*;


public class stall4 {
    static int N;
    static int M;
    static int[][] map;

    public static void main(String[] args) throws Exception  {
        BufferedReader br = new BufferedReader(new FileReader("stall4.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // cow to stall
        map = new int[N+1][M+1];
        for (int i = 1; i <= N; i++)  {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 1; j <= num; j++)  {
                map[i][Integer.parseInt(st.nextToken())] += 1;
            }
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("stall4.out")));
        int ans = solveOptimally();
        pw.println(ans);
        pw.close();

        System.out.println(ans);


    }

    public static int solveOptimally()  {
        int[] matchR = new int[N+1];
        Arrays.fill(matchR, -1);

        int total = 0;
        for (int cow = 1; cow <= N; cow++)  {
            boolean[] seen = new boolean[M+1];

            if (bpm(cow, seen, matchR))  {
                total++;
            }
        }
        return total;
    }

    public static boolean bpm(int cow, boolean[] seen, int[] matchR)  {
        for (int stall = 1; stall <= M; stall++)  {
            if (map[cow][stall] > 0 && !seen[stall])  {
                seen[stall] = true;

                if (matchR[stall] < 0 || bpm(matchR[stall], seen, matchR))  {
                    matchR[stall] = cow;
                    return true;
                }
            }
        }
        return false;
    }

}
