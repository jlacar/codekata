package com.github.jlacar.kata.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Simple String Calculator")
class StringCalculatorTest {

    private final StringCalculator calculator = new StringCalculator();

    @Nested
    @DisplayName("Addition feature: add()")
    class Addition {

        @Test
        @DisplayName("<empty string> == 0")
        void zero_for_empty() {
            assertEquals(0, calculator.add(""));
        }

        @Test
        @DisplayName("\"N\" == N")
        void one_number() {
            assertAll("one number",
                    () -> assertEquals(1, calculator.add("1")),
                    () -> assertEquals(2, calculator.add("2")),
                    () -> assertEquals(5, calculator.add("5"))
            );
        }

        @Test
        @DisplayName("\"N1,N2\" == N1 + N2")
        void two_numbers() {
            assertAll("two numbers",
                    () -> assertEquals(3, calculator.add("1,2")),
                    () -> assertEquals(6, calculator.add("2,4")),
                    () -> assertEquals(15, calculator.add("7,8"))
            );
        }

        @Test
        @DisplayName("\"N1,N2,..,Nn\" == N1 + N2 + ... + Nn")
        void multiple_numbers() {
            assertEquals(6, calculator.add("1,2,3"));
        }

        @Test
        @DisplayName("\"N1\\nN2\" == N1 + N2")
        void accepts_newline_separator() {
            assertAll("newline as separator",
                    () -> assertEquals(3, calculator.add("1\n2")),
                    () -> assertEquals(6, calculator.add("1\n2,3"))
            );
        }
    }

    @Nested
    @DisplayName("Custom delimiter feature")
    class CustomDelimiter {

        @Test
        @DisplayName("Recognizes //[delim]\\n to specify custom delimiter")
        void custom_delimiter() {
            assertEquals(3, calculator.add("//;\n1;2"));
        }
    }

}