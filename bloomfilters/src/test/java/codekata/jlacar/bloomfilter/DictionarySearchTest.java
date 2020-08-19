package codekata.jlacar.bloomfilter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DictionarySearchTest {

    private static final double TINY_PROBABILITY = 0.01;

    private MyBloomFilter baselineBloom;
    private Dictionary dict;

    private String wordThatIsThere;
    private String wordThatIsntThere;

    @Before
    public void setupDictionaries() {
        String[] words = { "mom", "apple", "cat", "sever", "apart", "moment", "applesauce", "applet", "category",
                "catalyst", "momentum", "several", "severity" };

        wordThatIsThere = words[1];
        wordThatIsntThere = "baseball";

        baselineBloom = new GuavaBloomFilter(words.length, TINY_PROBABILITY);

        dict = new Dictionary(baselineBloom);
        dict.insert(words);
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
        // dict.insert("ball"); // uncomment to make this test fail
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
    public boolean mightContain(String word);
    public boolean put(String word);
}

class CustomBloomFilter implements MyBloomFilter {

    @Override
    public boolean mightContain(String word) {
        return false;
    }

    @Override
    public boolean put(String word) {
        return false;
    }

}

class GuavaBloomFilter implements MyBloomFilter {

    private final com.google.common.hash.BloomFilter<String> filter;

    GuavaBloomFilter(int expectedInsertions) {
        filter = com.google.common.hash.BloomFilter.create(StringFunnel.INSTANCE, expectedInsertions);
    }
    
    GuavaBloomFilter(int expectedInsertions, double fpp) {
        filter = com.google.common.hash.BloomFilter.create(StringFunnel.INSTANCE, expectedInsertions,
                fpp);
    }

    @Override
    public boolean mightContain(String word) {
        return filter.mightContain(word);
    }

    @Override
    public boolean put(String word) {
        return filter.put(word);
    }
}