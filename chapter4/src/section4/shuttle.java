/*
ID: vhli2020
PROG: lgame
LANG: JAVA
 */

package section4;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class shuttle {
//    static int[] moves = new int[3000];
//    static char[] board;
//    static int N;
//    static int center;
//
//    public static void main(String[] args) throws Exception  {
//        N = 3;
//        center = N+1;
//        board = new char[2*N+2];
//        for (int i = 1; i <= N; i++)  {
//            board[i] = 'w';
//        }
//        board[N+1] = 'n';
//        for (int i = N+2; i <= 2*N+1; i++) {
//            board[i] = 'b';
//        }
//
//        int finalLength = solve(N+1, 1);
//
//        for (int i = 0; i < finalLength; i++)  {
//            System.out.print(moves[i] + " ");
//        }
//
//    }
//
//    public static int solve(int center,  int start)  {
//        // get into the alternation
//        int moveInStart = intoAlternate(center, N, start);
//
//        // do the hop
//        int moveOutStart = hopAlong(N, moveInStart + 1);
//
//
//        int finalLength = outOfAlternate(center, N, moveOutStart+1);
//        return finalLength;
//
//    }
//
//    // returns the finish thing
//    public static int intoAlternate(int center, int size, int start)  {
//        if (size == 1) {
//            moves[start] = center-1;
//            return start+1;
//        }
//
//        int newStart = intoAlternate(center, size-1, start);
//        if (size % 2 == 0)  {
//            moves[newStart] = 2*N + 1;
//        } else {
//            moves[newStart] =
//        }
//
//    }
//
//    public static int outOfAlternate(int center, int size, int start)  {
//        if (size == 1)  {
//            moves[start] = center;
//            return start+1;
//        }
//
//    }
//
//    public static int hopAlong(int size, int start)  {
//        int j = 0;
//        if (size % 2 == 0) {
//            for (int i = 2 * N - 1; i >= 0; i -= 2)  {
//                moves[start+j] = i;
//                j++;
//            }
//        } else {
//            for (int i = 3; i <= 2*N + 1; i += 2)  {
//                moves[start+j] = i;
//                j++;
//            }
//        }
//        return start+j;
//    }

}
