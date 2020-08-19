/*
ID: vhli2020
PROG: milk6
LANG: JAVA
 */

//package section4;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class milk6 {
    // N - number of warehouses, M - number of routes
    static int N;
    static int M;
    static int[][] weight;
    static int[][] residual;
    static int K = 40;

    static Map<Integer, ArrayList<Integer>> edgeToRoutes = new HashMap<>();

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("milk6.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        weight = new int[N+1][N+1];
        residual = new int[N+1][N+1];

        for (int i = 1; i <= M; i++)  {
            st = new StringTokenizer(br.readLine());
            int w1 = Integer.parseInt(st.nextToken());
            int w2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            weight[w1][w2] += cost;
            residual[w1][w2] += cost;

            int edgeID = K * w1 + w2;
            if (edgeToRoutes.containsKey(edgeID))  {
                edgeToRoutes.get(edgeID).add(i);
            } else {
                ArrayList<Integer> a = new ArrayList<>();
                a.add(i);
                edgeToRoutes.put(edgeID, a);
            }
        }

        int maxSize = findMinCut();
        ArrayList<Integer> edges = findBestEdges(maxSize);

        for (int i = 0; i < edges.size(); i++)  {
            System.out.println(i + " " + edges.get(i));
        }

        ArrayList<Integer> routesToShutDown = new ArrayList<>();
        for (int edge = 0; edge < edges.size(); edge++)  {
            System.out.println("Routes to shut down " + edges.get(edge));
            routesToShutDown.addAll(edgeToRoutes.get(edges.get(edge)));
        }
        Collections.sort(routesToShutDown);


        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milk6.out")));
        pw.println(maxSize + " " + routesToShutDown.size());
        for (int i = 0; i < routesToShutDown.size(); i++)  {
            pw.println(routesToShutDown.get(i));
        }
        pw.close();

    }

    public static ArrayList<Integer> findEdges()  {
        boolean[] reachable = new boolean[N+1];
        reachable[1] = true;

        Queue<Integer> q = new LinkedList<>();
        q.add(1);

        while (q.size() > 0) {
            int loc = q.poll();
            for (int i = 1; i <= N; i++)  {
                if (!reachable[i] && residual[loc][i] > 0)  {
                    reachable[i] = true;
                    q.add(i);
                }
            }
        }

        for (int i = 1; i <= N ; i++)  {
            System.out.println("Reachable at " + i + "is " + reachable[i]);
        }

        ArrayList<Integer> edges = new ArrayList<>();

        for (int w1 = 1; w1 <= N; w1++)  {
            for (int w2 = 1; w2 <= N; w2++)  {
                if (reachable[w1] && !reachable[w2] && weight[w1][w2] > 0)  {
                    int edgeID = K * w1 + w2;
                    edges.add(edgeID);
                }
            }
        }

        return edges;
    }

    public static ArrayList<Integer> findBestEdges(int sol)  {
        ArrayList<Integer> edgesID = new ArrayList<>();
        ArrayList<Integer> bestCut = new ArrayList<>();

        for (int w1 = 1; w1 <= N; w1++)  {
            for (int w2 = 1; w2 <= N; w2++)  {
                if (weight[w1][w2] > 0) {
                    edgesID.add(K * w1 + w2);
                }
            }
        }

        Queue<ArrayList<Integer>> q = new LinkedList<>();
        for (int i = 0; i < edgesID.size(); i++)  {
            ArrayList<Integer> a = new ArrayList<>();
            a.add(i);
            q.add(a);
        }

        while (q.size() > 0)  {
            ArrayList<Integer> cutEdgesID = q.poll();
            int cost = 0;
            for (int cEID = 0; cEID < cutEdgesID.size(); cEID++)  {
                int start = edgesID.get(cutEdgesID.get(cEID)) / K;
                int dest = edgesID.get(cutEdgesID.get(cEID)) % K;
                cost += weight[start][dest];
            }

            if (cost > sol)  {
                continue;
            }

            if (cost == sol)  {
                int numRoutes = 0;
                for (int cEID = 0; cEID < cutEdgesID.size(); cEID++)  {
                    numRoutes += edgeToRoutes.get(edgesID.get(cutEdgesID.get(cEID))).size();
                }
                if (bestCut.size() == 0 || numRoutes < bestCut.size())  {
                    ArrayList<Integer> cutEdges = new ArrayList<>();
                    for (int i = 0; i < cutEdgesID.size(); i++)  {
                        cutEdges.add(edgesID.get(cutEdgesID.get(i)));
                    }

                    if (!isFlow(cutEdges)) {
                        bestCut = cutEdgesID;
                    }
                }
            } else { // cost is less than solution
                int lastID = cutEdgesID.get(cutEdgesID.size() - 1);
                for (int i = lastID + 1; i <= N; i++)  {
                    ArrayList<Integer> al = new ArrayList<>(cutEdgesID);
                    al.add(i);
                    q.add(al);
                }
            }


            // find cost
            // if cost > sol - done
            // if cost == sol - find out which is the better solution
            // if cost < sol - add in more and keep going

        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < bestCut.size(); i++)  {
            ans.add(edgesID.get(bestCut.get(i)));
        }
        return ans;
    }

    public static boolean isFlow(ArrayList<Integer> edges)  {
        boolean[] reachable = new boolean[N+1];
        reachable[1] = true;

        for (int edge : edges)  {
            int start = edge / K;
            int dest = edge % K;
            residual[start][dest] = 0;
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        while (q.size() > 0)  {
            int w = q.poll();
            for (int i = 1; i <= N; i++)  {
                if (!reachable[i] && residual[w][i] > 0) {
                    reachable[i] = true;
                    q.add(i);
                }
            }
        }


        for (int edge : edges)  {
            int start = edge / K;
            int dest = edge % K;
            residual[start][dest] = weight[start][dest];
        }

        return reachable[N];
    }



    public static int findMinCut()  {
        int totalFlow = 0;

        int[] prev_node = new int[N+1];
        int pathFlow = findBestPath(prev_node);

        while (findBestPath(prev_node) > 0)  {
            totalFlow += pathFlow;

            int cur_node = N;
            while (cur_node != 1)  {
                int next_node = prev_node[cur_node];
                residual[next_node][cur_node] -= pathFlow;
                residual[cur_node][next_node] += pathFlow;


                cur_node = next_node;
            }

            prev_node = new int[N+1];
            pathFlow = findBestPath(prev_node);
        }
        return totalFlow;
    }

    public static int findBestPath(int[] prev_node)  {
        int[] flow = new int[N+1];
        flow[1] = Integer.MAX_VALUE;

        boolean[] seen = new boolean[N+1];

        while (true)  {
            int max_flow = 0;
            int max_loc = 0;

            for (int i = 1; i <= N; i++)  {
                if (!seen[i] && flow[i] > max_flow)  {
                    max_flow = flow[i];
                    max_loc = i;
                }
            }

            if (max_loc == 0 || max_loc == N)  {
                return flow[N];
            }

            seen[max_loc] = true;
            for (int i = 1; i <= N; i++)  {
                if (residual[max_loc][i] > 0)  {
                    if (flow[i] < Math.min(flow[max_loc], residual[max_loc][i])) {
                        flow[i] = Math.min(flow[max_loc], residual[max_loc][i]);
                        prev_node[i] = max_loc;
                    }
                }
            }

        }
    }
}
