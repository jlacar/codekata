package codekata.jlacar.bloomfilter;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class DictionarySearchTest {

    private Dictionary dict;
    
    @Test
    public void it_is_initially_empty() {
        dict = new Dictionary();

        assertTrue(dict.isEmpty());
    }

    @Test
    public void it_is_not_empty_after_inserting_words() throws Exception {
        dict = new Dictionary();
        dict.insert("mom", "apple", "cat", "severe", "appease");

        assertFalse(dict.isEmpty());
    }

    @Test
    public void it_can_check_if_word_is_definitely_contained() throws Exception {
        dict = minimalDictionary();
        
        assertTrue("minimal dictionary contains 'mom'", dict.contains("mom"));
        
    }

    @Test 
    public void it_can_check_if_word_is_definitely_not_contained() throws Exception {
        dict = minimalDictionary();
        
//        dict.insert("ball");  // uncomment to make this test fail
        assertFalse("minimal dictionary does not contain 'ball'", dict.contains("ball"));
    }

    @Test
    public void it_definitely_does_not_contain_word_when_bloom_filter_says_no() throws Exception {
        dict = minimalDictionary();
        
        assertFalse("minimal dictionary does not contain 'ball' (bloom)", 
                dict.mayContain("ball"));
    }
    
    @Test
    public void it_probably_contains_word_when_bloom_filter_says_maybe() throws Exception {
        dict = minimalDictionary();
        
        assertTrue("minimal dictionary does not contain 'ball' (bloom)", 
                dict.mayContain("ball"));
    }

    private Dictionary minimalDictionary() {
        Dictionary dict = new Dictionary();
        dict.insert("mom", "apple", "cat", "sever", "apart", "moment", "applesauce", "applet", "category", "catalyst",
                "momentum", "several", "severity");

        return dict;
    }

}
