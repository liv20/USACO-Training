/*
ID: vhli2020
PROG: shopping
LANG: JAVA
 */

package section3;

import javax.swing.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class shopping {
    static int S;
    static int B;
    static int[][] deals;
    // product code to order
    static Map<Integer, Integer> m = new HashMap<>();
    static int[] prices = new int[6];
    static int[] numNeeded = new int[6];
    static int[][][][][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("shopping.in"));
        S = Integer.parseInt(br.readLine());

        // deals[s][0] - price; deals[s][1, 2, 3, 4, 5] - product in order
        deals = new int[S][6];
        dp = new int[6][6][6][6][6];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[j].length; k++) {
                    for (int l = 0; l < dp[k].length; l++) {
                        Arrays.fill(dp[i][j][k][l], 100000);
                    }
                }
            }
        }
        dp[0][0][0][0][0] = 0;

        int itemNum = 0;
        for (int i = 0; i < S; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                int c = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());

                if (!m.containsKey(c)) {
                    itemNum++;
                    m.put(c, itemNum);
                }

                deals[i][m.get(c)] = k;
            }
            deals[i][0] = Integer.parseInt(st.nextToken());

        }

        for (int i = 0; i < deals.length; i++) {
            for (int j = 0; j < deals[0].length; j++) {
                System.out.print(deals[i][j] + " ");
            }
            System.out.println();
        }

        B = Integer.parseInt(br.readLine());
        for (int i = 0; i < B; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int code = Integer.parseInt(st.nextToken());
            if (!m.containsKey(code)) {
                itemNum++;
                m.put(code, itemNum);
            }
            numNeeded[m.get(code)] = Integer.parseInt(st.nextToken());
            prices[m.get(code)] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < numNeeded.length; i++) {
            System.out.print(numNeeded[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < prices.length; i++) {
            System.out.print(prices[i] + " ");
        }
        System.out.println();

        for (int p1 = 0; p1  <= numNeeded[1]; p1++) {
            for (int p2 = 0; p2 <= numNeeded[2]; p2++) {
                for (int p3 = 0; p3 <= numNeeded[3]; p3++) {
                    for (int p4 = 0; p4 <= numNeeded[4]; p4++) {
                        for (int p5 = 0; p5 <= numNeeded[5]; p5++) {
                            dp[p1][p2][p3][p4][p5] = p1*prices[1] + p2*prices[2] + p3*prices[3] +
                                    p4*prices[4] + p5*prices[5];
                        }
                    }
                }
            }
        }

        for (int i = 0; i < deals.length; i++) {
            for (int p1 = 0; p1 + deals[i][1] <= numNeeded[1]; p1++) {
                for (int p2 = 0; p2 + deals[i][2] <= numNeeded[2]; p2++) {
                    for (int p3 = 0; p3 + deals[i][3] <= numNeeded[3]; p3++) {
                        for (int p4 = 0; p4 + deals[i][4] <= numNeeded[4]; p4++) {
                            for (int p5 = 0; p5 + deals[i][5] <= numNeeded[5]; p5++) {
                                int priceWithDeal = dp[p1][p2][p3][p4][p5] + deals[i][0];
                                if (priceWithDeal < dp[p1+deals[i][1]][p2+deals[i][2]][p3+deals[i][3]][p4+deals[i][4]][p5+deals[i][5]]) {
                                    dp[p1+deals[i][1]][p2+deals[i][2]][p3+deals[i][3]][p4+deals[i][4]][p5+deals[i][5]] = priceWithDeal;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println(dp[numNeeded[1]][numNeeded[2]][numNeeded[3]][numNeeded[4]][numNeeded[5]]);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
        pw.println(dp[numNeeded[1]][numNeeded[2]][numNeeded[3]][numNeeded[4]][numNeeded[5]]);
        pw.close();

    }


}
