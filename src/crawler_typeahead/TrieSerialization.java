package crawler_typeahead;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Stack;
import java.util.TreeMap;

public class TrieSerialization {

	private class TrieNode {
		public NavigableMap<Character, TrieNode> children;
		public TrieNode() {
			children = new TreeMap<Character, TrieNode>();
		}
	}
	
	public String serialize(TrieNode root) {
		if (root == null)	return "";
		
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		Iterator<Entry<Character, TrieNode>> iter = root.children.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Character, TrieNode> entry = iter.next();
			Character key = entry.getKey();
			TrieNode child = (TrieNode)entry.getValue();
			sb.append(key);
			sb.append(serialize(child));
		}
		sb.append(">");
		return sb.toString();
	}
	
	public TrieNode deserialize(String data) {
		if (data == null || data.length() == 0) {
			return null;
		}
		
		TrieNode root = new TrieNode();
		TrieNode current = root;
		Stack<TrieNode> path = new Stack<TrieNode>();
		for (Character c : data.toCharArray()) {
			switch (c) {
			case '<':
				path.push(current);
				break;
			case '>':
				path.pop();
				break;
			default:
				current = new TrieNode();
				path.peek().children.put(c, current);
			}
		}
		
		return root;
	}
}
