/*
ID: vhli2020
PROG: lgame
LANG: JAVA
 */

package section3;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


public class lgame {
    static int max = 0;
    static char[] input;
    static Map<Character, Integer> val = new HashMap<>();
    static Map<String, Integer> scores = new HashMap<>();

    static ArrayList<Character> letters = new ArrayList<>();
    static ArrayList<Integer> occ = new ArrayList<>();

    static ArrayList<String> winners = new ArrayList<>();

    public static void loadVal()  {
        val.put('a', 2);
        val.put('b', 5);
        val.put('c', 4);
        val.put('d', 4);
        val.put('e', 1);
        val.put('f', 6);
        val.put('g', 5);
        val.put('h', 5);
        val.put('i', 1);
        val.put('j', 7);
        val.put('k', 6);
        val.put('l', 3);
        val.put('m', 5);
        val.put('n', 2);
        val.put('o', 3);
        val.put('p', 5);
        val.put('q', 7);
        val.put('r', 2);
        val.put('s', 1);
        val.put('t', 2);
        val.put('u', 4);
        val.put('v', 6);
        val.put('w', 6);
        val.put('x', 7);
        val.put('y', 5);
        val.put('z', 7);
    }

    public static void loadLettersAndOcc()  {
        for (int i = 0; i < input.length; i++)  {
            if (letters.contains(input[i])) {
                int index = letters.indexOf(input[i]);
                occ.set(index, occ.get(index) + 1);
            } else {
                occ.add(1);
                letters.add(input[i]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        loadVal();
        BufferedReader br = new BufferedReader(new FileReader("lgame.in"));
        input = br.readLine().toCharArray();
        br.close();
        Arrays.sort(input);
        loadLettersAndOcc();

        BufferedReader dictReader = new BufferedReader(new FileReader("lgame.dict"));
        String line = null;
        while ((line = dictReader.readLine()).length() >= 3) {
            char[] a = line.toCharArray();
            int score = getScore(a);

            if (score > 0)  {
                for (Map.Entry<String, Integer> item : scores.entrySet())  {
                    int doubleScore = getDoubleScore(a, item.getKey().toCharArray());
                    System.out.println(item.getKey() + " " + new String(a));
                    System.out.println(doubleScore);
                    if (doubleScore == max)  {
                        winners.add(item.getKey() + " " + new String(a));
                    } else if (doubleScore > max)  {
                        max = doubleScore;
                        winners = new ArrayList<>();
                        winners.add(item.getKey() + " " + new String(a));
                    }
                }
                scores.put(new String(a), score);

                if (score > max)  {
                    max = score;
                    winners = new ArrayList<>();
                    winners.add(new String(a));
                } else if (score == max)  {
                    winners.add(new String(a));
                }
            }

        }
        dictReader.close();

        // Sort the strings
        for (int i = 0; i < winners.size(); i++)  {
            for (int j = i + 1; j < winners.size(); j++)  {
                if (winners.get(i).compareTo(winners.get(j)) > 0) {
                    String temp = winners.get(i);
                    winners.set(i, winners.get(j));
                    winners.set(j, temp);
                }
            }
        }


        System.out.println("dict Reader closed");
        for (Map.Entry<String, Integer> item: scores.entrySet())  {
            System.out.println(item.getKey());
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lgame.out")));
        pw.println(max);
        for (int i = 0; i < winners.size(); i++)  {
            pw.println(winners.get(i));
        }
        pw.close();

    }

    public static int getScore(char[] a) {
        int[] thisOcc = new int[letters.size()];
        for (int i = 0; i < a.length; i++)  {
            if (letters.contains(a[i]))  {
                int index = letters.indexOf(a[i]);
                thisOcc[index] += 1;
            } else {
                return 0;
            }
        }

        int score = 0;
        for (int i = 0; i < letters.size(); i++)  {
            if (occ.get(i) < thisOcc[i])  {
                return 0;
            } else {
                score += (thisOcc[i] * val.get(letters.get(i)));
            }
        }
        return score;
    }

    public static int getDoubleScore(char[] a, char[] b) {
        if (a.length + b.length > input.length) {
            return 0;
        }

        char[] c = ((new String(a)) + new String(b)).toCharArray();
        return getScore(c);
    }

}
