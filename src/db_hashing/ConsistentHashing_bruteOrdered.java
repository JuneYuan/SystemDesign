package db_hashing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

//所谓 brute 是指，寻找被分割区间时采用的是遍历所有区间的方法
//Run Time: 9764 ms
public class ConsistentHashing_bruteOrdered {
	
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
			int insertPos = 1;
			for (int j = 0; j < results.size(); j++) {
				Machine m = results.get(j);
				if (m.compareTo(chosen) > 0) {
					insertPos = j + 1;
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
			results.add(insertPos, newMachine);
			
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
