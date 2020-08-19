/*
ID: vhli2020
PROG: butter
LANG: JAVA
 */

//package section2;

import javax.swing.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class butter {
    static int N, P, C;
    static List[] pastures;
    static List<Integer> cows;
    static int[][] distances;

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        BufferedReader br = new BufferedReader(new FileReader("butter.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        cows = new ArrayList<Integer>();
        pastures = new List[P];
        for (int i=0; i<P;i++) {
            pastures[i]=new ArrayList<int[]>();
        }
        for (int i=0; i<N; i++) {
            cows.add(Integer.parseInt(br.readLine())-1);
        }

        for (int i=0; i<C; i++) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken())-1;
            int second = Integer.parseInt(st.nextToken())-1;
            int distance = Integer.parseInt(st.nextToken());
            pastures[first].add(new int[] {second, distance});
            pastures[second].add(new int[] {first, distance});
        }
        distances = new int[P][P];
        br.close();

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
        out.println(solve());
        out.close();
        // print final time taken
        System.out.println(System.currentTimeMillis() - startTime);
        System.exit(0);

    }

    public static class Node implements Comparable {
        int distance;
        boolean visited;
        int id;

        @Override
        public int compareTo(Object other) {
            return this.distance-((Node)other).distance;
        }
    }

    public static int solve() {
        int min = Integer.MAX_VALUE;
        for (int cow : cows) {
            dijkstra(cow);
        }

        for (int i=0; i<P; i++) {
            int result = 0;
            for (int cow : cows) {
                result += distances[cow][i];
                if (result == Integer.MAX_VALUE) break;
            }
            min = Math.min(result, min);
        }
        return min;
    }

    public static void dijkstra(int begin) {
        Node[] nodes = new Node[P];
        for (int i = 0; i < P; i++) {
            nodes[i] = new Node();
            nodes[i].id = i;
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited = false;
        }
        PriorityQueue<Node> heap = new PriorityQueue<Node>();

        nodes[begin].distance = 0;
        nodes[begin].visited = true;
        Collections.addAll(heap, nodes);
        while (!heap.isEmpty()) {
            // the first node is nodes[begin]
            Node node = heap.poll();
            if (node.distance==Integer.MAX_VALUE) break;

            node.visited = true;
            for (Object object : pastures[node.id]) {
                int[] arr = (int[])object;
                if (nodes[arr[0]].distance>node.distance + arr[1]) {
                    nodes[arr[0]].distance = node.distance + arr[1];
                    if (!nodes[arr[0]].visited) {
                        heap.remove(nodes[arr[0]]);
                        heap.add(nodes[arr[0]]); // removing and adding lets heap reorder
                    }
                }
            }
        }
        for (Node node: nodes) {
            distances[begin][node.id] = node.distance;
        }


    }

}
