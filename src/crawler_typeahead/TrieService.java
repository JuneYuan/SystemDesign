package crawler_typeahead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TrieService {

    private TrieNode root = null;

    public TrieService() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        // Return root of trie root, and 
        // lintcode will print the tree struct.
        return root;
    }

    // @param word a string
    // @param frequency an integer
    public void insert(String word, int frequency) {
        // Write your cod here
        TrieNode curr = root;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            char ch = word.charAt(i);
            if (!curr.children.containsKey(ch)) {
                curr.children.put(ch, new TrieNode());
            }
            curr = curr.children.get(ch);
            addFrequency(curr.top10, frequency);
        }
    }
    
    private void addFrequency(List<Integer> top10, int frequency) {
        top10.add(frequency);
        Collections.sort(top10, Collections.reverseOrder());
        if (top10.size() > 10) {
            top10.remove(top10.size() - 1);
        }
    }
 }

class TrieNode {
	public NavigableMap<Character, TrieNode> children;
	public List<Integer> top10;
	
	public TrieNode() {
		children = new TreeMap<Character, TrieNode>();
		top10 = new ArrayList<Integer>();
	}
}