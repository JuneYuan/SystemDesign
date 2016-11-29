package crawler_typeahead;

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
            Character ch = word.charAt(i);
            if (!curr.children.containsKey(ch)) {
                curr.children.put(c, new TrieNode());
            }
            curr = curr.children.get(ch);
            addFrequency(curr.top10, frequency);
        }
    }
    
    private void addFrequency(List<Integer> top10, int frequency) {
        top10.add(frequency);
        int n = top10.size();
        int index = n - 1;
        while (index > 0) {
            if (top10.get(index) > top10.get(index - 1)) {
                int tmp1 = top10.get(index);
                int tmp2 = top10.get(index - 1);
                top10.set(index, tmp2);
                top10.set(index, tmp1);
                index -= 1;
            } else {
                break;
            }
        }
        
        if (n > 10) {
            top10.remove(n - 1);
        }
    }
 }
