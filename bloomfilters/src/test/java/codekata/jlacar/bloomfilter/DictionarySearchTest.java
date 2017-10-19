package codekata.jlacar.bloomfilter;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionarySearchTest {

    @Test
    public void it_is_initially_empty() {
        Dictionary dict = new Dictionary();
        
        assertTrue(dict.isEmpty());
    }
    
    @Test
    public void it_is_not_empty_after_inserting_words() throws Exception {
        Dictionary dict = new Dictionary();
        dict.insert("mom", "apple", "cat", "severe", "appease");
        
        assertFalse(dict.isEmpty());
    }
    
    private Dictionary newDictionary(String... entries) {
        return new Dictionary();
    }
    
}
