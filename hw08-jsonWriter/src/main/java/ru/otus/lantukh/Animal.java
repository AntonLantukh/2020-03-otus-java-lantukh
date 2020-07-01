package ru.otus.lantukh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Animal {
    private int age;
    private Integer height;
    private String name;
    private String[] friends;
    private Integer[] numbers;
    private Collection<String> collection;

    public Animal (
            int age,
            int height,
            String name,
            String[] friends,
            Integer[] numbers,
            ArrayList<String> collection
    ) {
        this.age = age;
        this.height = height;
        this.name = name;
        this.friends = friends;
        this.numbers = numbers;
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", height=" + height +
                ", name='" + name + '\'' +
                ", friends=" + Arrays.toString(friends) +
                '}';
    }
}
