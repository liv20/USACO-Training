/*
ID: vhli2020
PROG: nuggets
LANG: JAVA
 */

//package section1;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class nuggets {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("nuggets.in"));
        int N = Integer.parseInt(br.readLine());
        int[] values = br.lines().mapToInt(Integer::parseInt).toArray();
        br.close();

        int[] primesRun = new int[] {2, 3};

        boolean done = isDone(values);

        int max = 0;
        if(!done) {
            Set<Integer> set = new HashSet<Integer>();
            set.add(0);
            for(int i = 1; i <= 64261; i++) {
                for(int j : primesRun) {
                    if((i % j == 0 && set.contains(i / j))) {
                        set.add(i);
                        break;
                    }
                }
                if(!set.contains(i)) {
                    for(int value : values) {
                        if(set.contains(i - value)) {
                            set.add(i);
                            break;
                        }
                    }
                }
                if(!set.contains(i)) {
                    max = i;
                }
            }
            System.out.println(set);
        }
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nuggets.out")));
        out.println(max);
        out.close();
        System.exit(0);
    }

    public static boolean isDone(int[] values) {
        int gcd = values[0];
        for (int i = 1; i < values.length; i++) {
            gcd = gcd(gcd, values[i]);
        }

        if (gcd == 1) {
            return false;
        } else {
            return true;
        }
    }

    public static int gcd(int a , int b) {
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        } else {
            if (a > b) {
                return gcd(a % b, b);
            } else {
                return gcd(a, b % a);
            }
        }
    }
}
