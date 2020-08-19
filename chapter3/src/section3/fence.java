/*
ID: vhli2020
PROG: fence
LANG: JAVA
 */

//package section3;

import javax.swing.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class fence {
    static int F, circuitpos;
    static Node[] nodes;
    static int[] circuit;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("fence.in"));
        F = Integer.parseInt(br.readLine());

        nodes = new Node[501];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < F; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());

            nodes[first].neighbors.add(second);
            nodes[second].neighbors.add(first);
        }
        circuit = new int[F+1];

        for (int i = 0; i < nodes.length; i++) {
            Collections.sort(nodes[i].neighbors);
        }
        br.close();



        findEulerCircuit();

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));
        for (int i = F; i >= 0; i--) {
            out.println(circuit[i]);
        }
        out.close();

        System.exit(0);

    }

    public static void findEulerCircuit() {
        circuitpos = 0;

        for (int i = 1; i < nodes.length; i++) {
            if (nodes[i].neighbors.size() % 2 == 1) {
                findCircuit(i);
                return;
            }
        }
        findCircuit(1);
    }

    public static void findCircuit(int i) {

        while (nodes[i].neighbors.size() > 0) {
            int j = nodes[i].neighbors.remove(0);
            nodes[j].neighbors.remove((Integer) i);
            findCircuit(j);
        }

        circuit[circuitpos] = i;
        circuitpos++;
    }


    static class Node {
        List<Integer> neighbors = new ArrayList<>();
    }

}
