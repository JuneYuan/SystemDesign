package db_hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class MiniCassandra {
    private Map<String, NavigableMap<Integer, String>> records;

    public MiniCassandra() {
        // initialize your data structure here.
		records = new HashMap<String, NavigableMap<Integer, String>>();
    }
    
    /**
     * @param raw_key a string
     * @param column_start an integer
     * @param column_end an integer
     * @return void
     */
    public void insert(String rowKey, int colKey, String colValue) {
        // Write your code here
		if (!records.containsKey(rowKey)) {
			records.put(rowKey, new TreeMap<Integer, String>());
		}
		records.get(rowKey).put(colKey, colValue);
    }

    /**
     * @param raw_key a string
     * @param column_start an integer
     * @param column_end an integer
     * @return a list of Columns
     */
    public List<Column> query(String rowKey, int colStart, int colEnd) {
        // Write your code here
		List<Column> result = new ArrayList<>();
		if (!records.containsKey(rowKey)) {
			return result;
		}
		Set<Map.Entry<Integer, String>> cells = records.get(rowKey).subMap(colStart, colEnd + 1).entrySet();
		for (Map.Entry<Integer, String> cell : cells) {
			result.add(new Column(cell.getKey(), cell.getValue()));
		}
		
		return result;
    }
}

class Column {
	private int key;
	private String value;
	
	public Column(int key, String value) {
		this.key = key;
		this.value = value;
	}
}
