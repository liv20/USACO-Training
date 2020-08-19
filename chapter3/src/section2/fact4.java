/*
ID: vhli2020
PROG: fact4
LANG: JAVA
 */

package section2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class fact4 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("fact4.in"));
        int N = Integer.parseInt(br.readLine());

        int[] divs = new int[N+1];
        divs[0] = 1;
        for (int j = 1; j <= N; j++) {
            divs[j] = j;
        }

        int zeroes = determineNumZeroes(N);

        int twos = 0;
        int fives = 0;

        int i = 0;
        while (twos < zeroes && i <= N) {
            if (divs[i] % 2 == 0) {
                divs[i] /= 2;
                twos++;
            }
            else {
                i++;
            }
        }
        i = 0;
        while (fives < zeroes && i <= N) {
            if (divs[i] % 5 == 0) {
                divs[i] /= 5;
                fives++;
            }
            else {
                i++;
            }
        }

        for (int j = 0; j < N+1; j++) {
            System.out.print(divs[j] + " ");
        }

        int product = divs[0];
        for (int j = 1; j < N+1; j++) {
            product *= divs[j];
            product %= 10;
        }

        System.out.println();
        System.out.println(product);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
        pw.println(product);
        pw.close();

    }

    public static int determineNumZeroes(int N) {
        int num_zeroes = 0;

        while (N > 0) {
            N /= 5;
            num_zeroes += N;
        }
        return num_zeroes;
    }
}
