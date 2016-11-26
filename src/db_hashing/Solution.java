package db_hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

// ConsistentHashingII_microShards
public class Solution {
    private int n;    // 大区间圆环的长度
    private int k;    // 每台机器的 micro-shards 数目（每次新加入一台机器，就在圆周上撒 k 个点）
    private Set<Integer> machinehashcodes = null;  // 机器机器分身的 hashcode 值，用以区别机器与数据
    private Map<Integer, List<Integer>> machines = null;

    private Solution(int n, int k, Set<Integer> machineIds, Map<Integer, List<Integer>> machines) {
        this.n = n;
        this.k = k;
        this.machinehashcodes = machineIds;
        this.machines = machines;
    }

    // @param n a positive integer
    // @param k a positive integer
    // @return a Solution object
    public static Solution create(int n, int k) {
        // Write your code here
        Set<Integer> machineIds = new TreeSet<>();
        Map<Integer, List<Integer>> machines = new HashMap<Integer, List<Integer>>();
        Solution solution = new Solution(n, k, machineIds, machines);
        return solution;
    }

    // @param machine_id an integer
    // @return a list of shard ids
    public List<Integer> addMachine(int machine_id) {
        // Write your code here
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int index;
            do {
                index = random.nextInt(n);
            } while (machinehashcodes.contains(index));
            
            result.add(index);
            machinehashcodes.add(index);
        }
        
        return result;
    }

    // @param hashcode an integer
    // @return a machine id
    public int getMachineIdByHashCode(int hashcode) {
        // Write your code here    	
    	int distance = n + 1;
        int result = 0;
        for (Map.Entry<Integer, List<Integer>> entry : machines.entrySet()) {
            int machineId = entry.getKey();
            List<Integer> dataOnMachine = entry.getValue();
            for (Integer data : dataOnMachine) {
                int d = data - hashcode;
                if (d < 0) {
                    d += n;
                } else if (d < distance) {
                    distance = d;
                    result = machineId;
                }
                
            }
        }
        
        return result;
    }

}
