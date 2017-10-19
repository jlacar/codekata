package codekata.jlacar.bloomfilter;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionarySearchTest {

    @Test
    public void test() {
        Dictionary dict = newDictionary("");
        assertEquals(0, dict.size());
    }
    
    private Dictionary newDictionary(String... entries) {
        return new Dictionary();
    }
    
}
