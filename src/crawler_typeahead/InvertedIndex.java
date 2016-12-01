package crawler_typeahead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
	
    public Map<String, List<Integer>> invertedIndex(List<Document> docs) {
        // Write your code here
        if (docs == null || docs.size() == 0)  return null;
        Map<String, List<Integer>> result = new HashMap<>();
        for (Document doc : docs) {
            String[] words = doc.content.split("\\s");
            Arrays.sort(words);
            for (int i = 0; i < words.length; i++) {
                if (words[i].length() == 0)
                    continue;
                if (i > 0 && words[i].equals(words[i - 1]))
                    continue;
                    
                if (!result.containsKey(words[i])) {
                    List<Integer> list = new ArrayList<>();
                    result.put(words[i], list);
                }
                result.get(words[i]).add(doc.id);
            }
        }
        
        return result;
    }
}

class Document {
	public int id;
	public String content;
}