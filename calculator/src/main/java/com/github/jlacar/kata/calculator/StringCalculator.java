package com.github.jlacar.kata.calculator;

import java.util.Arrays;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = "[\n,]";
    private static final String CUSTOM_DELIMITER_PREFIX = "//";

    static final Adder DEFAULT_ADDER = new Adder(DEFAULT_DELIMITER);

    public int add(String s) {
        Adder adder = DEFAULT_ADDER;

        if (s.startsWith(CUSTOM_DELIMITER_PREFIX)) {
            adder = new Adder(customDelimiter(s));
            s = s.substring(s.indexOf('\n') + 1);
        }

        return adder.add(s);
    }

    private String customDelimiter(String s) {
        return s.lines().findFirst().get().substring(2);
    }
}

class Adder {
    private final String delimiter;

    Adder(String delimiter) {
        this.delimiter = delimiter;
    }

    int add(String s) {
        if (s.isBlank()) {
            return 0;
        }
        return Arrays.stream(s.split(this.delimiter))
                .mapToInt(Integer::valueOf)
                .sum();
    }
}
