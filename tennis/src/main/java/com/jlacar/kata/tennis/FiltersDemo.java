package com.jlacar.kata.tennis;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.jlacar.kata.tennis.FiltersDemo.Subset.CONNECTION;
import static com.jlacar.kata.tennis.FiltersDemo.Subset.RELATIVE;
import static java.util.stream.Collectors.*;

public class FiltersDemo {

    // Just a container; don't instantiate this
    private FiltersDemo() {}

    public static enum Subset {
        RELATIVE, CONNECTION;
    }

    public static enum OptionFilter {
        PARENT(RELATIVE, "1"),
        CHILD(RELATIVE, "2"),
        SPOUSE(RELATIVE, "3"),
        SIBLING(RELATIVE, "4"),
        OWNER(CONNECTION, "7"),
        CEO(CONNECTION, "8"),
        COLLEAGUE(CONNECTION, "9"),
        EMPLOYEE(CONNECTION, "10");

        private final Subset subset;
        private final String code;

        private OptionFilter(Subset subset, String code) {
            this.subset = subset;
            this.code = code;
        }

        public Subset getSubset() {
            return this.subset;
        }

        public String getCode() {
            return this.code;
        }

        // stick with similar semantics as built-in values() method
        public static OptionFilter[] values(Subset subset) {
            List<OptionFilter> subList = Arrays.stream(values())
                    .filter(e -> e.subset == subset)
                    .collect(toList());
            OptionFilter[] results = new OptionFilter[subList.size()];
            IntStream.range(0, subList.size())
                    .forEach(i -> results[i] = subList.get(i));
            return results;
        }
    }

    public static void main(String[] args) {
        printSubset("relative");
        printSubset("connection");
        printSubset("foobar");
    }

    private static void printSubset(String subsetName) {
        try {
            Subset subset = Subset.valueOf(subsetName.toUpperCase());

            // print all the values in the given subset
            OptionFilter[] enums = OptionFilter.values(subset);
            System.out.printf("%sS: %s%n", subset, Arrays.toString(enums));

            // print all their codes, too
            List<String> codes = Arrays.stream(enums).map(OptionFilter::getCode).collect(toList());
            System.out.printf("Codes: %s%n", codes.stream().collect(joining("\", \"", "[\"", "\"]")));

            System.out.println();
        } catch (IllegalArgumentException noSuchSubset) {
            System.out.println("No such subset!");
        }
    }
}

