/*
ID: vhli2020
PROG: job
LANG: JAVA
 */

//package section2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class job {

    public static void main(String[] args) throws Exception  {
        BufferedReader br = new BufferedReader(new FileReader("job.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int m1Count = Integer.parseInt(st.nextToken());
        int m2Count = Integer.parseInt(st.nextToken());

        int[] m1 = new int[m1Count];
        int[] m2 = new int[m2Count];

        st = new StringTokenizer(br.readLine());
        int i = 0;
        while (i < m1Count)  {
            if (st.hasMoreTokens())  {
                m1[i] = Integer.parseInt(st.nextToken());
                i++;
            } else {
                st = new StringTokenizer(br.readLine());
            }
        }

        i = 0;
        while (i < m2Count)  {
            if (st.hasMoreTokens())  {
                m2[i] = Integer.parseInt(st.nextToken());
                i++;
            } else {
                st = new StringTokenizer(br.readLine());
            }
        }

        Arrays.sort(m1);
        Arrays.sort(m2);


        int[] ans = solve(N, m1Count, m2Count, m1, m2);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("job.out")));
        pw.println(ans[0] + " " + ans[1]);
        pw.close();

    }

    public static int[] solve(int N, int m1Count, int m2Count, int[] m1, int[] m2)  {
        int TA = totalTimeOneMachine(N, m1Count, m1);
        int TB = totalTimeOneMachine(N, m2Count, m2);
        int d = findDelayTime(N, m1Count, m2Count, m1, m2, TA, TB);
        int[] ans = new int[2];
        ans[0] = TA;
        ans[1] = TB + d;


        System.out.println(TA);
        System.out.println(TB);
        System.out.println(d);

        return ans;
    }

    public static int totalTimeOneMachine(int N, int machineCount, int[] machineTimes)  {
        int t = 0;
        while (true)  {
            t += 1;
            int jobsDone = 0;
            for (int i = 0; i < machineCount; i++)  {
                jobsDone += (t / machineTimes[i]);
            }

            if (jobsDone >= N)  {
                break;
            }
        }
        return t;
    }

    public static int findDelayTime(int N, int m1Count, int m2Count, int[] m1, int[] m2, int TA, int TB)  {
        int d = 1;

        outer: while (true)  {
            for (int t = 0; t < TB; t++)  {
                int intermediateCount = finish(N, m1Count, m1, d+t);
                int backwardsTBCount = N - finish(N, m2Count, m2, TB - t) + adjust(m2Count, m2, TB, t);

                System.out.println("T: " + t);
                System.out.println("D: " + d);
                System.out.println("Intermediate Ct: " + intermediateCount);
                System.out.println("BackwardsTBCount: " + backwardsTBCount);

                if (intermediateCount < backwardsTBCount)  {
                    d += 1;
                    continue outer;
                }
            }
            break;
        }

        return d;
    }

    public static int adjust(int machineCount, int[] machineTimes, int TB, int t)  {
        int adjustment = 0;
        for (int i = 0; i < machineCount; i++)  {
            if ((TB - t) % machineTimes[i] == 0)  {
                adjustment++;
            }
        }
        return adjustment;
    }



    // returns the number of jobs that are available at time t using the list of machine times
    public static int finish(int N, int machineCount, int[] machineTimes, int t)  {
        int jobs = 0;
        for (int i = 0; i < machineCount; i++)  {
            jobs += (t / machineTimes[i]);
        }
        return Math.min(N, jobs);
    }
}
