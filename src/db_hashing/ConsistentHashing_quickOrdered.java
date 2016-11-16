package db_hashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

public class ConsistentHashing_quickOrdered {
	private static final Comparator<Machine> BY_INTERVAL = new ByInterval();

	public List<List<Integer>> consistentHashing(int n) {
		List<Machine> machines = solve(n);
		return adaptDataType(machines);	
	}
	
	private List<Machine> solve(int n) {
		List<Machine> results = new ArrayList<Machine>();
		PriorityQueue<Machine> pq = new PriorityQueue<>(Collections.reverseOrder());
		
		Machine machine = new Machine(0, 359, 1);		
		results.add(machine);
		pq.add(machine);
		for (int i = 1; i < n; i++) {
			// choose one machine to spare its interval
			Machine chosen = pq.poll();
			
			// adjust interval bounds of the chosen machine
			int end = chosen.rightBound;
			int mid = (chosen.leftBound + chosen.rightBound) / 2;
			chosen.rightBound = mid;
			chosen.span = chosen.rightBound - chosen.leftBound + 1;
			pq.add(chosen);
			
			// insert the new machine
			Machine newMachine = new Machine(mid + 1, end, i + 1);			
			results.add(newMachine);
			pq.add(newMachine);
		}
		
		Collections.sort(results, BY_INTERVAL);
		return results;	
	}

	private static class ByInterval implements Comparator<Machine> {

		@Override
		public int compare(Machine m1, Machine m2) {
			return m1.leftBound - m2.leftBound;
		}
		
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
