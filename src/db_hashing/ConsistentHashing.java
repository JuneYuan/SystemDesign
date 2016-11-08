package db_hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

public class ConsistentHashing {
	private final int MOD = 360;
	
	private class Machine implements Comparable<Machine> {
		int id;  // 1, 2, 3, ...
		int leftBound, rightBound;  // this machine occupies interval [leftBound, rightBound]
		int span;
		
		public Machine(int leftBound, int rightBound, int id) {
			this.leftBound = leftBound;
			this.rightBound = rightBound;
			this.id = id;
			this.span = rightBound - leftBound + 1;
		}

		@Override
		public int compareTo(Machine that) {
			// Java 的优先队列默认是小根堆，所以这里采用“相反”的比较规则
			// span 越大的，排序结果越靠前；区间位置越靠近原点的，排序结果越靠前
			if (this.span > that.span)  return -1;
			if (this.span < that.span)  return 1;
			if (this.id < that.id)  return -1;
			if (this.id > that.id)  return 1;
			return 0;
		}
	
		@Override
		public String toString() {
			return String.format("(%d, %d, %d)", leftBound, rightBound, id);
		}
	}

    /**
     * @param n a positive integer
     * @return n x 3 matrix
     */
    public List<List<Integer>> consistentHashing(int n) {
        // Write your code here
        List<List<List<Integer>>> buf = new ArrayList<List<List<Integer>>>(360);
        compute(buf);
        
        return buf.get(n - 1);
    }
    
    private void compute(List<List<List<Integer>>> buf) { 
        List<Machine> machines = new ArrayList<>();			// sequence of all machines
        PriorityQueue<Machine> pq = new PriorityQueue<>();  // priority queue of above machines
        
        Machine m = new Machine(0, 359, 1);
        machines.add(m);
        pq.add(m);
        
        // 先初始化 n = 0 （一台机器）时的情况
        List<List<Integer>> oneBufItem = new ArrayList<List<Integer>>(1);
        oneBufItem.add(Arrays.asList(m.leftBound, m.rightBound, m.id));
        buf.add(oneBufItem);
        
        for (int n = 1; n < MOD; n++) {
        	if (n == 3)
        		System.out.print(" ");
        	
        	// choose one machine to spare its interval
        	Machine chosen = pq.remove();
        	
            // adjust interval bounds of the chosen machine
        	chosen.rightBound = (chosen.leftBound + chosen.rightBound) / 2;
        	chosen.span = chosen.rightBound - chosen.leftBound + 1;
        	pq.add(chosen);
        	
        	// insert the new machine
            int pos = machines.indexOf(chosen) + 1;
        	m = new Machine(chosen.rightBound + 1, chosen.rightBound + chosen.span, n + 1);
            machines.add(pos, m);
            pq.add(m);
            
            // add one more item to buf
        	oneBufItem = new ArrayList<List<Integer>>();
            for (int i = 0; i < machines.size(); i++) {
            	Machine mm = machines.get(i);
            	oneBufItem.add(Arrays.asList(mm.leftBound, mm.rightBound, mm.id));
            }
        	buf.add(oneBufItem);
        }
    }

    @Test
    public void test() {
    	for (int i = 1; i <= 5; i++)
    		System.out.println(consistentHashing(i));
    }
    
    @Test
    public void testMachineCompare() {
    	PriorityQueue<Machine> pq = new PriorityQueue<>();
    	Machine m0 = new Machine(0, 89, 1);
    	Machine m1 = new Machine(90, 179, 3);
    	Machine m2 = new Machine(180, 359, 2);
    	pq.add(m0);
    	pq.add(m1);
    	pq.add(m2);
    	
    	System.out.println(pq.remove());
    	System.out.println(pq.remove());
    	System.out.println(pq.remove());
    }

}
