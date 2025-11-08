/**
 * @author dxy
 * @version 1.0
 * 钢条切割问题
 */

public class CutRod {
    public static void main(String[] args) {
        int[] p = {0, 1, 5, 8, 9};  // 价格表
        Bottom2Up_Cut(p, 10);
    }

    private static void Bottom2Up_Cut(int[] p, int n) {

        int[] r = new int[n + 1];
        int[] cut = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            r[i] = 0;
            cut[i] = 0;
        }

        int tpr, l;
        for (int i = 1; i <= n; i++) {
            l = Math.min(i, p.length - 1);
            for (int j = 1; j <= l; j++) {
                tpr = p[j] + r[i - j];
                if (tpr > r[i]) {
                    r[i] = tpr;
                    cut[i] = j;
                }
            }
        }

        System.out.println("\n=== DP 表 ===");
        for (int i = 1; i <= n; i++) {
            System.out.printf("长度 %2d : 收益 = %2d, 首刀 = %d%n", i, r[i], cut[i]);
        }

        System.out.println("\n最大收益 r[" + n + "] = " + r[n]);
        System.out.print("最优切割方案: ");
        int len = n;
        while (len > 0) {
            System.out.print(cut[len] + " ");
            len -= cut[len];
        }
        System.out.println();
    }
}
