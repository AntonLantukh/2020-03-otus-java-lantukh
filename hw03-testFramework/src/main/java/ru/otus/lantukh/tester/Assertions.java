package ru.otus.lantukh.tester;

public class Assertions {
    public static void assertTrue(boolean condition) {
        if (!condition) {
            System.err.println("Expecting condition to equal true");
            throw new AssertionException("Assert true failed");
        }
    }

    public static void assertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            System.err.println("Expecting " + expected + " to equal " + actual);
            throw new AssertionException("Assert true failed");
        }
    }
}
