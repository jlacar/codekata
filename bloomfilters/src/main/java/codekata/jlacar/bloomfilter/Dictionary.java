package codekata.jlacar.bloomfilter;

import java.util.Map;
import java.util.TreeMap;

public class Dictionary {

    private Map<String, String> dict = new TreeMap<>();
    
    private int size;
    
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    public void add(String... words) {
        for (String w : words) {
            dict.put(w, null);
        }
    }

}
