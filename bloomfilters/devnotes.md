# Development Notes

## Background

See http://codekata.com/kata/kata05-bloom-filters/ for background information

## Initial thoughts

Instead of jumping right into writing a BloomFilter class, let's set up a context for using bloom filters instead. Developers tend to want to jump right into the nitty-gritty details and this is what often gets them in trouble: They get overwhelmed with too much detail too soon.

To avoid this trap, we'll start with trying to define *what* we want to do at a high level. We'll use our tests to drive out our high-level API first.

So, let's start with a test called DictionarySearchTest.

## First Cut at the High-level API

Let's think about what we want to do with a Dictionary. Adding entries will naturally be our first task. Again, developers often get caught up in the details of this and will often want to jump right to file I/O operations. Let's back up from that because that has proven to be a deep  rabbit hole in the past. Instead, let's define a more abstract way to add entries to the dictionary.

### The first test

The first test will often get refactored away later but you have to start somewhere, even if it's the wrong place. The first test created asserts that a new Dictionary will have no entries. So, we assert that the ``size()`` is 0 and make sure the implementation code is such that the test fails. Making the test pass is a simple matter of returning the correct value of 0.
 