/*
ID: vhli2020
PROG: race3
LANG: JAVA
 */

package section3;

import java.io.*;
import java.util.*;


public class race3 {
    static ArrayList<node> track = new ArrayList<>();
    static int end;

    public static void main(String[] args) throws Exception  {
        BufferedReader br = new BufferedReader(new FileReader("race3.in"));

        String line = br.readLine();
        int lineNumber = 0;
        while (true)  {
            node n = new node(lineNumber);

            StringTokenizer st = new StringTokenizer(line);
            int i = Integer.parseInt(st.nextToken());
            if (i == -1)  {
                break;
            }

            while (i != -2)  {
                n.addArrow(i);
                i = Integer.parseInt(st.nextToken());
            }
            track.add(n);

            line = br.readLine();
            lineNumber += 1;
        }
        end = track.size()-1;

        for (int i = 0; i < track.size(); i++)  {
            System.out.println(track.get(i).getString());
        }

        ArrayList<Integer> unavoidables = findUnavoidables();
        ArrayList<Integer> splitingPoints = new ArrayList<>();

        for (int i = 0; i < unavoidables.size(); i++)  {
            if (isSplittingPoint(unavoidables.get(i)))  {
                splitingPoints.add(unavoidables.get(i));
            }
        }

        System.out.println();
        for (int i = 0; i < unavoidables.size(); i++) {
            System.out.print(unavoidables.get(i) + " ");
        }


        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("race3.out")));
        pw.print(unavoidables.size());
        for (int i = 0; i < unavoidables.size(); i++)  {
            pw.print(" " + unavoidables.get(i));
        }
        pw.println();
        pw.print(splitingPoints.size());
        for (int i = 0; i < splitingPoints.size(); i++)  {
            pw.print(" " + splitingPoints.get(i));
        }
        pw.println();
        pw.close();

        System.out.println(isSplittingPoint(6));

    }

    public static ArrayList<Integer> findUnavoidables()  {
        ArrayList<Integer> unavoidables = new ArrayList<>();
        eachPoint: for (int i = 1; i < end; i++)  {
            Set<Integer> visited = new HashSet<>();
            visited.add(0);
            Queue<Integer> q = new LinkedList<>();
            q.add(0);
            while (q.size() > 0)  {
                int loc = q.poll();
                for (int j = 0; j < track.get(loc).out.size(); j++)  {
                    int outLoc = track.get(loc).out.get(j);
                    if (outLoc == end)  {
                        continue eachPoint;
                    }

                    if (outLoc != i && !visited.contains(outLoc)) {
                        q.add(outLoc);
                        visited.add(outLoc);
                    }
                }
            }

            unavoidables.add(i);
        }
        return unavoidables;
    }

    public static boolean isSplittingPoint(int point)  {
        boolean[] reachable = new boolean[end+1];
        reachable[0] = true;

        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        Queue<Integer> q = new LinkedList<>();
        q.add(0);

        while (q.size() > 0)  {
            int loc = q.poll();

            for (int j = 0; j < track.get(loc).out.size(); j++)  {
                int newLoc = track.get(loc).out.get(j);

                if (!visited.contains(newLoc) && newLoc != loc && newLoc != point)  {
                    visited.add(newLoc);
                    q.add(newLoc);

                    reachable[newLoc] = true;
                }
            }
        }

        for (int i = 0; i < reachable.length; i++)  {
            System.out.print(i + ": " + reachable[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < track.size(); i++)  {
            if (!reachable[i]) {
                for (int j = 0; j < track.get(i).out.size(); j++)  {
                    if (reachable[track.get(i).out.get(j)])  {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static class node {
        ArrayList<Integer> out = new ArrayList<>();
        int id;

        public node(int id)  {
            this.id = id;
        }

        public void addArrow(int id)  {
            out.add(id);
        }

        public String getString() {
            String s = id + ":  ";
            for (int i = 0; i < out.size(); i++)  {
                s += out.get(i) + " ";
            }
            return s;
        }
    }
}
