package codekata.jlacar.bloomfilter;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionarySearchTest {

    @Test
    public void new_Dictionary_is_empty() {
        Dictionary dict = new Dictionary();
        
        assertTrue(dict.isEmpty());
    }
    
    @Test
    public void adding_words_makes_it_not_empty() throws Exception {
        Dictionary dict = new Dictionary();
        dict.add("mom", "apple", "cat", "severe", "appease");
        
        assertFalse(dict.isEmpty());
    }
    
    private Dictionary newDictionary(String... entries) {
        return new Dictionary();
    }
    
}
