# Development Notes

## Background

See http://codekata.com/kata/kata05-bloom-filters/ for background information

## Initial thoughts

Instead of jumping right into writing a BloomFilter class, let's set up a context for using bloom filters instead. Developers tend to want to jump right into the nitty-gritty details and this is what often gets them in trouble: They get overwhelmed with too much detail too soon.

To avoid this trap, well start with trying to define *what* we want to do at a high level. We'll use our tests to drive out our high-level API first.

So, let's start with a test called DictionarySearchTest.
 