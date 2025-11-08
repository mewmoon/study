import java.util.*;

/**
 * @author dxy
 * @version 1.0
 * 最长公共子序列
 */
public class LCS{
    public static void main(String[] args){
        String[] X = {"A","B","C","B","D","A","B"};
        String[] Y= {"B","D","C","A","B","A"};

        LCSXY(X, Y);
    }

    private static void LCSXY(String[] X, String[] Y) {
        int m = X.length;
        int n = Y.length;
        int[][] C = new int[m+1][n+1];

        String[] di = {"o","↑","←","↖"};
        String[][] B = new String[m+1][n+1];

       ArrayList<String> z = new ArrayList<>();

        for(int i = 0; i <= m ; i++){
            C[i][0] = 0;
            B[i][0] = di[0];
        }
        for(int j = 0; j <= n; j++){
            C[0][j] = 0;
            B[0][j] = di[0];
        }

        for (int i = 1; i <= m; i++){
           for (int j = 1; j <= n; j++){
               if(X[i-1].equals(Y[j-1])){
                   C[i][j] = C[i-1][j-1] + 1;
                   B[i][j] = di[3];
               } else {
                   C[i][j] = Math.max(C[i-1][j], C[i][j-1]);
                   if(C[i-1][j] > C[i][j-1]){
                       B[i][j] = di[1];
                   } else {
                       B[i][j] = di[2];
                   }
               }
           }
        }

        System.out.println("====== DP matrix =====");
        for (int i = 0; i <= m; i++){
            for (int j = 0; j <= n; j++){
                System.out.print(C[i][j] + " " + B[i][j] + " \t");
            }
            System.out.println();
        }

        System.out.println("====== LCS =====");

        Stack<String> st = new Stack<>();

        int i = m;
        int j = n;

        while (C[i][j]>0) {
            if(C[i][j] > C[i-1][j] & C[i][j] > C[i][j-1]){
                System.out.println(C[i][j] + " "+ X[i-1] + " " + Y[j-1]);
                st.push(X[i-1]);
                i = i-1;
                j = j-1;
            } else if (C[i][j] > C[i-1][j]){
                j = j-1;
            } else {
                i = i-1;
            }
        }

        while(!st.isEmpty()){
            System.out.print(st.pop());
        }

    }
}
