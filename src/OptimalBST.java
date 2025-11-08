/**
 * @author dxy
 * @version 1.0
 * 最优二叉搜索树问题
 */
public class OptimalBST {
    public static void main(String[] args) {
//        int[] k = {2, 5, 8, 22};
//        float[] p = {0.1F, 0.2F, 0.1F, 0.05F};
//        float[] q = {0.15F, 0.1F, 0.05F, 0.1F, 0.15F};
        int[] k = {2, 5};
        float[] p = {0.2F, 0.4F};
        float[] q = {0.1F, 0.1F, 0.2F};
        buildOptimalBST(k, p, q);
    }

    private static void buildOptimalBST(int[] k, float[] p, float[] q) {
        int N = k.length;
        float[][] E = new float[N + 1][N + 1];  //E[i][j]代表 di到dj 及其中包含的点ki+1到kj的  总搜索距离期望
        float[][] W = new float[N + 1][N + 1];  //W[i][j]代表 di到dj 及其中包含的点ki+1到kj的  总概率
        int[][] root = new int[N + 1][N + 1];      //root[i][j]代表 二叉树顶点k

        for (int l = 0; l <= N; l++) {
            E[l][l] = q[l];
            W[l][l] = q[l];
        }

        for (int l = 1; l <= N; l++) {
            //更新第l层 从E[0][l] 到 E[N-l][N]
            for (int i = 0; i <= N - l; i++) {
                int j = i + l;
                E[i][j] = Float.MAX_VALUE;
                W[i][j] = W[i][j - 1] + p[j - 1] + q[j];
                for (int u = i + 1; u <= j; u++) {
                    // k[u]是二叉树顶点，左子树E[i,u-1]    右子树E[u,j]
//                    float tmE = E[i][u - 1] + E[u][j] + W[i][u - 1] + W[u][j] + p[u - 1];
                    float tmE = E[i][u - 1] + E[u][j] + W[i][j];
                    if (tmE < E[i][j]) {
                        E[i][j] = tmE;
                        root[i][j] = u;
                    }
                }
            }
        }

        System.out.println("==========  DP  Matrix ==============");
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                System.out.print(E[i][j] + "("+root[i][j]+") \t");
            }
            System.out.println();
        }
    }
}
