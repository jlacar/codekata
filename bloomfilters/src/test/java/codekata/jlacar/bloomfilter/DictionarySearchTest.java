package codekata.jlacar.bloomfilter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class DictionarySearchTest {

    private static final double TINY_PROBABILITY = 0.01;
    
    private BloomFilter<String> baselineBloom;
    private Dictionary dict;
    
    private String wordThatIsThere;
    private String wordThatIsntThere;

    @Before
    public void setupDictionaries() {
        String[] words = {"mom", "apple", "cat", "sever", "apart", "moment", "applesauce", "applet", "category", "catalyst",
                "momentum", "several", "severity"};
        
        wordThatIsThere = words[1];
        wordThatIsntThere = "baseball";
        
        dict = new Dictionary();
        dict.insert(words);
        
        baselineBloom = BloomFilter.create(StringFunnel.INSTANCE, words.length, TINY_PROBABILITY);
        for (String w : words) {
            baselineBloom.put(w);
        }
    }

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
        assertTrue("minimal dictionary contains 'mom'", dict.contains("mom"));
    }

    @Test 
    public void it_can_check_if_word_is_definitely_not_contained() throws Exception {
//        dict.insert("ball");  // uncomment to make this test fail
        assertFalse("minimal dictionary does not contain 'ball'", dict.contains("ball"));
    }

    @Test
    public void it_definitely_does_not_contain_word_when_bloom_filter_says_no() throws Exception {
        assertThat(baselineBloom.mightContain(wordThatIsntThere), is(false));
        assertThat(dict.mayContain(wordThatIsntThere), is(false));
    }
    
    @Test
    public void it_probably_contains_word_when_bloom_filter_says_maybe() throws Exception {
        assertThat(baselineBloom.mightContain(wordThatIsThere), is(true));
        assertThat(dict.mayContain(wordThatIsThere), is(true));
    }
        
    @Test
    public void it_can_be_instantiated_with_a_bloom_filter_implementation() throws Exception {
        dict = new Dictionary(new CustomBloomFilter());
    }
}

interface MyBloomFilter {
    
}

class CustomBloomFilter implements MyBloomFilter {
    
}