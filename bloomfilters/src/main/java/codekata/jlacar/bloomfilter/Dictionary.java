package codekata.jlacar.bloomfilter;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class Dictionary {
    
    private static int DEFAULT_BLOOM_SIZE = 500;
    
    private Map<String, String> dict = new TreeMap<>();
    private BloomFilter<String> bloomFilter;
    
    public Dictionary() {
        bloomFilter = BloomFilter.create(StringFunnel.INSTANCE, DEFAULT_BLOOM_SIZE);
    }
    
    public Dictionary(int size) {
        bloomFilter = BloomFilter.create(StringFunnel.INSTANCE, size);
    }
    
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    public final void insert(String... words) {
        for (String w : words) {
            dict.put(w, null);
            bloomFilter.put(w);
        }
    }

    public boolean contains(String word) {
        return dict.containsKey(word);
    }

    public boolean mayContain(String word) {
        return bloomFilter.mightContain(word);
    }

}

enum StringFunnel implements Funnel<String> {
    INSTANCE;

    @Override
    public void funnel(String word, PrimitiveSink into) {
        into.putUnencodedChars(word);
    }
}
