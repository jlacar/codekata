package codekata.jlacar.bloomfilter;

import static org.junit.Assert.*;

import org.junit.Ignore;
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

    @Test
    public void it_can_check_if_word_is_definitely_contained() throws Exception {
        Dictionary dict = minimalDictionary();
        
        assertTrue("minimal dictionary contains 'mom'", dict.contains("mom"));
        
    }

    @Test 
    public void it_can_check_if_word_is_definitely_not_contained() throws Exception {
        Dictionary dict = minimalDictionary();
        
//        dict.insert("ball");  // uncomment to make this test fail
        assertFalse("minimal dictionary does not contain 'ball'", dict.contains("ball"));
    }
    
    // TODO
    @Ignore
    @Test
    public void it_can_search_probabilistically() throws Exception {}

    private Dictionary minimalDictionary() {
        Dictionary dict = new Dictionary();
        dict.insert("mom", "apple", "cat", "sever", "apart", "moment", "applesauce", "applet", "category", "catalyst",
                "momentum", "several", "severity");

        return dict;
    }

}
