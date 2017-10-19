package codekata.jlacar.bloomfilter;

import java.util.Map;
import java.util.TreeMap;

public class Dictionary {

    private Map<String, String> dict = new TreeMap<>();
    
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    public void insert(String... words) {
        for (String w : words) {
            dict.put(w, null);
        }
    }

}
