/*
ID: vhli2020
PROG: ditch
LANG: JAVA
 */

package section2;


import java.io.*;
import java.util.*;

public class ditch {
    static int N;
    static int M;
    static int[][] adjm;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("ditch.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        adjm = new int[M+1][M+1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int last = Integer.parseInt(st.nextToken());
            int flow = Integer.parseInt(st.nextToken());

            adjm[first][last] += flow;
        }
        br.close();

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("ditch.out")));
        pw.println(solveOptimally());
        pw.close();

    }


    public static int solveOptimally()  {
        int total_flow = 0;

        int[] prev_node = new int[M+1];
        int pathFlow = findMaximumFlow(prev_node);
        // while maximum flow is greater than zero, keep going
        while (pathFlow > 0)  {
            total_flow += pathFlow;

            // make sure to update the adjm table as reminded in the algorithm--it's for the flow
            int cur_node = M;
            while (cur_node != 1)  {
                int next_node = prev_node[cur_node];
                adjm[next_node][cur_node] -= pathFlow;
                adjm[cur_node][next_node] += pathFlow;
                cur_node = next_node;
            }

            // reset
            prev_node = new int[M+1];
            pathFlow = findMaximumFlow(prev_node);
        }

        return total_flow;
    }

    public static int findMaximumFlow(int[] prev_node)  {
        // for djikstra's, you need 3 things: prev_node array, visited array, and a distance/flow array
        int[] flow = new int[M+1];
        flow[1] = Integer.MAX_VALUE;

        boolean[] visited = new boolean[M+1];

        while (true)  {
            int max_flow = 0;
            int max_loc = 0;

            for (int i = 1; i <= M; i++)  {
                if (!visited[i] && flow[i] > max_flow)  {
                    max_flow = flow[i];
                    max_loc = i;
                }
            }

            if (max_loc == 0 || max_loc == M)  {
                break;
            }

            visited[max_loc] = true;
            for (int i = 1; i <= M; i++)  {
                if (adjm[max_loc][i] > 0)  {
                    if (flow[i] < Math.min(max_flow, adjm[max_loc][i]))  {
                        prev_node[i] = max_loc;
                        flow[i] = Math.min(max_flow, adjm[max_loc][i]);
                    }
                }
            }
        }

        return flow[M];
    }
}
