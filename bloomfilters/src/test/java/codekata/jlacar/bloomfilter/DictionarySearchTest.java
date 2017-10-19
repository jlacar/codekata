package codekata.jlacar.bloomfilter;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionarySearchTest {

    @Test
    public void new_Dictionary_is_empty() {
        Dictionary dict = newDictionary("");
        assertEquals(0, dict.isEmpty());
    }
    
    private Dictionary newDictionary(String... entries) {
        return new Dictionary();
    }
    
}
