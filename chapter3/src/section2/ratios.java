package section2;

/*
ID: vhli2020
PROG: ratios
LANG: JAVA
 */

import java.io.*;
import java.util.*;

public class ratios {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("ratios.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] goal = new int[3];
        goal[0] = Integer.parseInt(st.nextToken());
        goal[1] = Integer.parseInt(st.nextToken());
        goal[2] = Integer.parseInt(st.nextToken());

        int[][] feeds = new int[3][3];
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            feeds[i][0] = Integer.parseInt(st.nextToken());
            feeds[i][1] = Integer.parseInt(st.nextToken());
            feeds[i][2] = Integer.parseInt(st.nextToken());
        }

        int ansA = 0;
        int ansB = 0;
        int ansC = 0;
        int ansN = 0;
        int[] res = new int[3];
        found:
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                for (int c = 0; c < 100; c++) {
                    res = getTotal(feeds, a, b, c);
                    if (isMultiple(res, goal)) {
                        ansA = a;
                        ansB = b;
                        ansC = c;
                        ansN = res[0] / goal[0];
                        break found;
                    }
                }
            }
        }

        System.out.println(ansA);
        System.out.println(ansB);
        System.out.println(ansC);
        System.out.println(ansN);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));
        if (ansN == 0) {
            pw.print("NONE\n");
        } else {
            pw.print(ansA);
            pw.print(" ");
            pw.print(ansB);
            pw.print(" ");
            pw.print(ansC);
            pw.print(" ");
            pw.print(ansN);
            pw.print("\n");
        }
        pw.close();


    }

    public static int[] getTotal(int[][] f, int a, int b, int c) {
        int[] res = new int[3];
        for (int i = 0; i < 3; i++) {
            res[i] = f[0][i] * a + f[1][i] * b + f[2][i] * c;
        }
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void printDoubleArray(int[][] darr) {
        for (int i = 0; i < darr.length; i++) {
            printArray(darr[i]);
        }
    }

    public static boolean isMultiple(int[] na, int[] a) {
        if (na[0] == 0 && na[1] == 0 && na[2] == 0) {
            return false;
        }

        int multiple = 0;
        if (a[0] != 0) {
            multiple = na[0] / a[0];
        }
        if (a[1] != 0) {
            multiple = na[1] / a[1];
        }
        if (a[2] != 0) {
            multiple = na[2] / a[2];
        }

        if (a[0] * multiple == na[0] && a[1] * multiple == na[1] && a[2] * multiple == na[2]) {
            return true;
        }
        return false;

    }

    public static boolean isIntMultiple(int na, int a) {
        if (a == 0) {
            return na == 0;
        } else {
            return na % a == 0;
        }
    }

}
