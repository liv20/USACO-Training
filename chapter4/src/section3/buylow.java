/*
ID: vhli2020
PROG: buylow
LANG: JAVA
 */

package section3;

import java.io.*;
import java.util.*;

public class buylow {
    public static void main(String[] args) throws Exception  {
        BufferedReader br = new BufferedReader(new FileReader("buylow.in"));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = 0;
        int[] days = new int[N];
        while (n < N) {
            if (st.hasMoreTokens()) {
                days[n] = Integer.parseInt(st.nextToken());
                n++;
            } else {
                st = new StringTokenizer(br.readLine());
            }
        }
        br.close();

        String[] ans = solve(N, days);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("buylow.out")));
        pw.println(ans[0] + " " + ans[1]);
        pw.close();

    }

    public static String[] solve(int N, int[] days)  {
        int longest = 0;
        int index = 0;

        int[] f = new int[N];
        Arrays.fill(f, 1);
        for (int n = 0; n < N; n++)  {
            for (int j = 0; j < n; j++)  {
                if (days[j] > days[n])  {
                    f[n] = Math.max(f[n], f[j]+1);
                }
            }

            if (f[n] > longest)  {
                longest = f[n];
                index = n;
            }
        }


        BigNum[] g = new BigNum[N];
        for (int n = 0; n < N; n++)  {
            g[n] = new BigNum(0);
        }

        for (int n = 0; n < N; n++)  {
            if (f[n] == 1)  {
                g[n] = new BigNum(1);
            } else {
                int prevLength = f[n] - 1;
                int last = -1;
                //Set<Integer> set = new HashSet<>();
                for (int j = n-1; j >= 0; j--)  {
                    if (days[j] > days[n] && prevLength == f[j] && days[j] != last)  {
                        //set.add(days[j]);
                        last = days[j];
                        g[n] = g[n].addTo(g[j]);
                    }
                }
            }
        }

        int last = -1;
        BigNum numSequences = new BigNum(0);
        for (int n = N-1; n >= 0; n--)  {
            if (f[n] == longest && days[n] != last)  {
                numSequences = numSequences.addTo(g[n]);
                //numSequences += g[n];
                last = days[n];
            }
        }


        for (int i = 0; i < N; i++)  {
            System.out.print(f[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < N; i++)  {
            System.out.print(g[i] + " ");
        }
        System.out.println();

        System.out.print(longest);

        String[] ans = new String[2];
        ans[0] = Integer.toString(longest);

        ans[1] = numSequences.getString();
        return ans;
    }

    static class BigNum {
        ArrayList<Integer> rep = new ArrayList<>();

        public BigNum(Integer i) {
            if (i == 0)  {
                rep.add(0);
            }
            while (i != 0)  {
                rep.add(i % 10);
                i = i/10;
            }
        }

        public BigNum(ArrayList<Integer> a) {
            for (int i = 0; i < a.size(); i++)  {
                rep.add(a.get(i));
            }
        }

        public String getString()  {
            String s = "";
            for (int i = rep.size() - 1; i >= 0; i--)  {
                s += rep.get(i).toString();
            }
            return s;
        }

        public BigNum addTo(BigNum other)  {
            boolean carry = false;
            int computations = Math.max(other.rep.size(), this.rep.size());
            ArrayList<Integer> a = new ArrayList<>();

            for (int comp = 0; comp < computations; comp++)  {
                int sum = 0;
                if (comp < this.rep.size()) {
                    sum += this.rep.get(comp);
                }
                if (comp < other.rep.size()) {
                    sum += other.rep.get(comp);
                }
                if (carry) {
                    sum += 1;
                }


                if (sum >= 10) {
                    carry = true;
                    sum -= 10;
                } else {
                    carry = false;
                }

                a.add(sum);
            }

            if (carry)  {
                a.add(1);
            }

            return new BigNum(a);

        }

    }
}



