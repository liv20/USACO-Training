/*
ID: vhli2020
PROG: agrinet
LANG: JAVA
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class agrinet {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("agrinet.in"));
        int N = Integer.parseInt(br.readLine());

        int[][] matrix = new int[N+1][N+1];

        for (int i = 1; i <= N; i++) {
            int j = 1;
            while (j <= N) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                while (st.hasMoreTokens()) {
                    matrix[i][j] = Integer.parseInt(st.nextToken());
                    j++;
                }
            }
        }
        br.close();

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        // 1. initialize arrays that keep track of distance from tree,
        // if the node is in the tree, and the sources for each

        // distance(j) is smallest distance from tree to j;
        // j must be directly connected to not have an actual distance
        // source(j) is the node of tree closest to j
        int[] distance = new int[N+1];
        boolean[] intree = new boolean[N+1];
        int[] source = new int[N+1];

        int treesize = 1;
        int cost = 0;
        intree[1] = true;

        // for each neighbor of i
        for (int i = 1; i <= N; i++) {
            distance[i] = matrix[1][i];
            source[i] = 1;
        }

        // 2. use a while loop as the treesize increases
        while (treesize < N) {
            // find node with minimum distance to tree
            int min_distance = 100000;
            int min_node = 0;
            for (int j = 1; j <= N; j++) {
                if (intree[j]) {
                    for (int i = 1; i <= N; i++) {
                        if (i != j && matrix[j][i] < min_distance && !intree[i]) {
                            min_distance = matrix[j][i];
                            min_node = i;
                        }
                    }
                }
            }
            assert (min_distance != 100000); // otherwise, graph is not connected

            // put in tree
            treesize += 1;
            cost += min_distance;
            intree[min_node] = true;

            // update distance after node i added
            for (int j = 1; j <= N; j++) {
                if (j != min_node && distance[j] > matrix[min_node][j]) {
                    distance[j] = matrix[min_node][j];
                    source[j] = min_node;
                }
            }

        }

        System.out.println(cost);


        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("agrinet.out")));
        pw.println(cost);
        pw.close();
    }
}
