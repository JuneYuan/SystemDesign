package crawler_typeahead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    /**
     * @param docs a list of documents
     * @return an inverted index
     */
    public Map<String, List<Integer>> invertedIndex(List<Document> docs) {
        // Write your code here
        if (docs == null || docs.size() == 0)  return null;
        Map<String, List<Integer>> result = new HashMap<>();
        for (Document doc : docs) {
            String[] words = doc.content.split("\\s");
            for (String word : words) {
                if (!result.containsKey(word)) {
                    List<Integer> list = new ArrayList<>();
                    result.put(word, list);
                }
                result.get(word).add(doc.id);
            }
        }
        
        return result;
    }

}

class Document {
	public int id;
	public String content;
}