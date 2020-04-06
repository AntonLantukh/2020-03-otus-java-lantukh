package ru.otus.lantukh;

import com.google.common.base.Splitter;

public class HelloOtus {
    public static void main(String[] args) {
        String str = "a, b,c, d, e";
        Iterable<String> strArray = Splitter.on(',').trimResults().split(str);

        for (String letter: strArray) {
            System.out.println(letter);
        }
    }
}
