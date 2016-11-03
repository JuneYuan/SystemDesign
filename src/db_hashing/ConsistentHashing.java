package db_hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsistentHashing {

    /**
     * @param n a positive integer
     * @return n x 3 matrix
     */
    public List<List<Integer>> consistentHashing(int n) {
        // Write your code here
        ArrayList<List<List<Integer>>> buf = new ArrayList<List<List<Integer>>>(360);
        
        compute(buf);
        
        return buf.get(n - 1);
    }
    
    private void compute(List<List<List<Integer>>> buf) {
        int sz = buf.size();
        
        // 先初始化 n 为0时的情况
        for (int n = 1; n < sz; n++) {
            List<List<Integer>> oneResult = new List<ArrayList<>>(n + 1);
            
            oneResult.set(最大区间下标, Arrays.asList(原左边界, 区间长度减半后的新右边界, 最大区间编号));
            oneResult.add(哪个下标, Arrays.asList(上一行的新右边界 + 1, 原右边界, n));
            
            buf.add(oneResult);
        }
    }

}
