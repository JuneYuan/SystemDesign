package db_hashing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

// 所谓 brute 是指，寻找被分割区间时采用的是遍历所有区间的方法
public class ConsistentHashing_brute {
	private class Machine implements Comparable<Machine> {
		int id;						// 1, 2, 3, ...
		int leftBound, rightBound;  // this machine occupies interval [leftBound, rightBound]
		int span;					// interval length
		
		public Machine(int leftBound, int rightBound, int id) {
			this.leftBound = leftBound;
			this.rightBound = rightBound;
			this.id = id;
			this.span = rightBound - leftBound + 1;
		}

		public List<Integer> toList() {
			List<Integer> result = new ArrayList<>();
			result.add(leftBound);
			result.add(rightBound);
			result.add(id);
			
			return result;
		}
		
		@Override
		public int compareTo(Machine that) {
			if (this.span < that.span)  return -1;
			if (this.span > that.span)  return 1;
			if (this.id > that.id)  return -1;
			if (this.id < that.id)  return 1;
			return 0;
		}
	
		@Override
		public String toString() {
			return String.format("(%d, %d, %d)", leftBound, rightBound, id);
		}
	}
	
	public List<List<Integer>> consistentHashing(int n) {
		List<Machine> machines = solve(n);
		return adaptDataType(machines);	
	}
	
	private List<Machine> solve(int n) {
		List<Machine> results = new ArrayList<Machine>();
		
		Machine machine = new Machine(0, 359, 1);		
		results.add(machine);
		for (int i = 1; i < n; i++) {
			// choose one machine to spare its interval
			Machine chosen = new Machine(0, 0, 0);
			for (Machine m : results) {
				if (m.compareTo(chosen) > 0) {
					chosen = m;
				}
			}
			
			// adjust interval bounds of the chosen machine
			int end = chosen.rightBound;
			int mid = (chosen.leftBound + chosen.rightBound) / 2;
			chosen.rightBound = mid;
			chosen.span = chosen.rightBound - chosen.leftBound + 1;			
			
			// insert the new machine
			Machine newMachine = new Machine(mid + 1, end, i + 1);			
			results.add(newMachine);
		}
		
		return results;	
	}

	private List<List<Integer>> adaptDataType(List<Machine> machines) {
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		for (Machine m : machines) {
			results.add(m.toList());
		}

		return results;
	}
	
    @Test
    public void test() {
    	for (int i = 1; i <= 5; i++)
    		System.out.println(consistentHashing(i));
    }

}
