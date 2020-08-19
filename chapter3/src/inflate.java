/*
ID: vhli2020
PROG: inflate
LANG: JAVA
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class inflate {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("inflate.in"));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[] max = new int[M+1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int points = Integer.parseInt(st.nextToken());
            int minutes = Integer.parseInt(st.nextToken());

            for (int j = minutes; j < M+1; j++) {
                if (max[j] < max[j-minutes] + points) {
                    max[j] = max[j-minutes] + points;
                }
            }

        }

        br.close();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("inflate.out")));
        pw.println(max[M]);
        pw.close();


    }
}
