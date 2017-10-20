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

Next, we refactor. The test name was left as ``test()`` so we give it a better name. We try ``new_Dictionary_is_empty`` and see that the method name ``size()`` reveals too much implementation detail. Let's try to make that more abstract.

The first refactoring suggests that maybe we took a little too big of a first step. The refactored code takes a smaller step by calling the Dictionary default constructor. Now the ``newDictionary()`` method we initially had isn't even used. We'll keep it anyway because we still want to come up with a high-level API for adding entries to the Dictionary.

### The second test

Now that we have an initial question we can ask a ``Dictionary`` object, we think about what kind of "conversation" we'd like to have that would require us to ask that question. That is, what is the context for calling the ``isEmpty()`` method? Is this really a useful capability to add to the Dictionary API? Will this eventually lead us to implementing a bloom filter? How?

Let's try to answer these questions by adding a new test that asserts the opposite of the first test: when words are added, the dictionary should not be empty. We see the test fails. That's good. Now we need to add some implementation code to make it work.

We need a data structure to hold our words. Let's try using a TreeMap. Let's just get the test to pass and worry about other considerations later. We put the words into the dictionary and fix the code in ``isEmpty()`` to work with the ``dict`` field instead of ``size``.

Running the tests, we get a green bar. Success! Now we refactor. First we delete the ``size`` field because it's no longer needed. Next, we look at the test names and see that there is not much consistency in the names. Let's rename them to establish some consistency. Let's use a more BDD style naming convention. After renaming the second test, we see that we used "inserting" in the test name but we called ``dict.add()`` in the body. Let's rename the method ``add()`` to ``insert()`` since we seem to favor the latter term.

### The third and fourth tests

Now we go back to the questions we asked before and try to expand on the context for using a Dictionary object. After we have inserted words in a Dictionary, we want to do a search. This now brings us closer to needing a bloom filter. However, before we go there, we also need to have a way to definitely check if a word is in the dictionary. Since a bloom filter can return a false positive, we need to be able to compare the result of a query that uses a bloom filter with the result of a query that checks the full list.

So, we first add a test for a method that will give us a definite yes/no answer instead of a definite no and maybe which a query based on a bloom filter would return. We add the test for the probabilistic search as a TODO that is ignored for now.

We see the test fail.

Now we fix implementation so the test passes. Success!

We refactor and rename the test methods and re-run the tests to make sure they still pass.

At this point, we see that since the third test checks for "definitely contains," we need a fourth test for "definitely does not contain." We short-circuit the TDD cycle for now and go ahead and add that test, see it fail by purposely adding a call to insert(), then make the test pass by commenting out the bogus setup statement. We keep it commented out so others can see what we did to make the test fail first.

## The context for testing a bloom filter

The first four test and implementation code gives us a basic context for developing a sequence of actions on a dictionary that will demonstrate how a bloom filter works. 

We start with a dictionary that has some known words already inserted into it. Then we'll make a probabilistic query that uses a bloom filter and compare the result we get with what a definitive query using a full search returns.

### The fifth and sixth tests

The fifth test drives our choice for the probabilistic query method's name, ``mayContain(String word)``. We start with the "definitely not contained" scenario because in this case, we don't have to deal with false positives. By making the implementation spuriously return ``false``, we can make the test pass even though we know the implementation is wrong. 

Adding a failing sixth test will force us to write a proper implementation of ``mayContain()``.

Looking around, we find that the Google Guava library already has a ``BloomFilter<T>`` implementation. We decide to use this as a baseline for comparison with our own implementation so we add a new dependency in the pom.xml. We notice that the JUnit dependency is still at 3.x so we also update that to version 4.12.

Looking at the Guava implementation, we get validation that we've chosen a good name for the probabilistic query because the Guava implementation has a similar name, ``mightContain()``. Nice to know we're on the same wavelength as the Guava guys.

