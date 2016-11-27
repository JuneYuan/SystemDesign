package db_hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

// 这里的 brute 是指，在 getMachineIdByHashCode() 的实现上，采用了直接顺时针扫描的无脑办法
public class ConsistentHashingII_microShards_brute {
    private int n;    // 大区间圆环的长度
    private int k;    // 每台机器的 micro-shards 数目，或称为“分身”数目（每次新加入一台机器，就在圆周上撒 k 个点）
    private Map<Integer, Integer> hashcode2MachineId;  //

    // @param n a positive integer
    // @param k a positive integer
    // @return a Solution object
    public static ConsistentHashingII_microShards_brute create(int n, int k) {
        // Write your code here
    	ConsistentHashingII_microShards_brute solution = new ConsistentHashingII_microShards_brute();
    	solution.n = n;
    	solution.k = k;
    	solution.hashcode2MachineId = new HashMap<Integer, Integer>();
    	return solution;
    }

    // @param machine_id an integer
    // @return a list of shard ids
    public List<Integer> addMachine(int machineId) {
        // Write your code here
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        
        Set<Integer> usedHashcodes = hashcode2MachineId.keySet();
        for (int i = 0; i < k; i++) {
            int hashcode;
            do {
            	hashcode = random.nextInt(n);
            } while (usedHashcodes.contains(hashcode));
            
            result.add(hashcode);
            hashcode2MachineId.put(hashcode, machineId);
        }
        
        return result;
    }

    // @param hashcode an integer
    // @return a machine id
    public int getMachineIdByHashCode(int hashcode) {
        // Write your code here    	
    	Integer microShard = Integer.valueOf(hashcode);
    	Integer machineId;
    	do {
    		microShard %= n;
    		machineId = hashcode2MachineId.get(microShard++);
    	} while (machineId == null);
    	
    	return machineId;
    }
}
