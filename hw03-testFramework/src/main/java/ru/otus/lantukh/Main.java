package ru.otus.lantukh;

import ru.otus.lantukh.tester.TestLauncher;

public class Main {
    public static void main(String[] args) {
        TestLauncher testLauncher = new TestLauncher();
        testLauncher.start("AnimalTest");
    }
}
