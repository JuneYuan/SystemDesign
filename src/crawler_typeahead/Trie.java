package crawler_typeahead;

import java.util.HashMap;

public class Trie {
    private TrieNode root;

    private class TrieNode {
    	private char value;
    	private boolean isEndOfWord;
    	private HashMap<Character, TrieNode> char2child = new HashMap<>();
    	
    	public TrieNode() {}
    	
    	public TrieNode(char value) {
    		this.value = value;
    	}
    	
    	public boolean hasChildOfChar(char ch) {
    		return char2child.containsKey(ch);
    	}

    	public TrieNode getChildOfChar(char ch) {
    		return char2child.get(ch);
    	}
    	
    	public void addChildOfChar(char ch) {
    		TrieNode node = new TrieNode(ch);
    		char2child.put(ch, node);
    	}
    	    	
    }
    
    public Trie() {
        root = new TrieNode();  // the dummy node
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
        	char ch = word.charAt(i);
        	if (!curr.hasChildOfChar(ch)) {
        		curr.addChildOfChar(ch);
        	}
        	curr = curr.getChildOfChar(ch);
        }
        
        curr.isEndOfWord = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode node = helper(word);
        return (node != null && node.isEndOfWord);
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = helper(prefix);
        return node != null;
    }
    
    private TrieNode helper(String str) {
    	TrieNode curr = root;
    	for (int i = 0; i < str.length(); i++) {
    		char ch = str.charAt(i);
    		if (!curr.hasChildOfChar(ch))
    			return null;
    		curr = curr.getChildOfChar(ch);
    	}
    	return curr;
    }
}
