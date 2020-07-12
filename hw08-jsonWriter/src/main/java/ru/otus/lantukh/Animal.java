package ru.otus.lantukh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class Animal {
    private int age;
    private Integer height;
    private String name;
    private String[] friends;
    private int[] numbers;
    private Collection<String> collection;

    public Animal (
            int age,
            int height,
            String name,
            String[] friends,
            int[] numbers,
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
                ", numbers=" + Arrays.toString(numbers) +
                ", collection=" + collection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;

        return age == animal.age &&
                Objects.equals(height, animal.height) &&
                Objects.equals(name, animal.name) &&
                Arrays.equals(friends, animal.friends) &&
                Arrays.equals(numbers, animal.numbers) &&
                Objects.equals(collection, animal.collection);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(age, height, name, collection);
        result = 31 * result + Arrays.hashCode(friends);
        result = 31 * result + Arrays.hashCode(numbers);
        return result;
    }
}
