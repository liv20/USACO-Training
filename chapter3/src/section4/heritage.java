/*
ID: vhli2020
PROG: heritage
LANG: JAVA
 */

package section4;

import java.util.*;
import java.io.*;

public class heritage {
    static String inorder;
    static String preorder;
    static int length;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("heritage.in"));
        inorder = br.readLine();
        preorder = br.readLine();
        length = inorder.length();
        System.out.println(length);

        String ans = getPostOrder(0, length-1, 0);
        System.out.print(ans);

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("heritage.out")));
        pw.println(ans);
        pw.close();

    }

    public static String getPostOrder(int start, int end, int preorderIndex) {
        if (start == end) {
            System.out.println(inorder.substring(start, start+1) + " " + start);
            return inorder.substring(start, start+1);
        } else if (start > end || end >= length || preorderIndex >= length) {
            return "";
        }
        int divide = inorder.indexOf(preorder.substring(preorderIndex, preorderIndex+1));
        System.out.println(preorder.substring(preorderIndex, preorderIndex+1) + " " + divide);

        String left = getPostOrder(start, divide-1, preorderIndex+1);
        String right = getPostOrder(divide+1, end, preorderIndex+(divide-start)+1);
        return left + right + inorder.substring(divide, divide+1);
    }


}
