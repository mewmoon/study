/**
 * @author dxy
 * @version 1.0
 * 流水作业调度问题
 */
public class FlowShopScheduling {
    public static void main(String[] args) {
        int[][] jobs = {
            {3, 2},  // 作业1在机器1、2的加工时间
            {2, 1},  // 作业2
            {4, 3},  // 作业3
            {1, 3}   // 作业4
        };
        schedule(jobs);
    }

    private static void schedule(int[][] jobs) {
        // 最优情况下，作业在两个流水线上的执行顺序是一样的？
        // TODO
    }
}
