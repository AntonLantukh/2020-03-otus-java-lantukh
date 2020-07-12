package ru.otus.lantukh;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyGson myGson = new MyGson();
        Gson gson = new Gson();

        // Object
        Animal animal1 = new Animal(
                12,
                20,
                "wolf",
                stringArrayFactory(),
                integerArrayFactory(),
                collectionFactory()
        );

        String json = myGson.toJson(animal1);
        Animal animal2 = gson.fromJson(json, Animal.class);
        System.out.println(animal1.equals(animal2));

        // Byte
        System.out.println(gson.toJson((byte) 1).equals(myGson.toJson((byte) 1)));

        // Short
        System.out.println(gson.toJson((short) 1f).equals(myGson.toJson((short) 1f)));

        // Integer
        System.out.println(gson.toJson(12).equals(myGson.toJson(12)));

        // Long
        System.out.println(gson.toJson(1L).equals(myGson.toJson(1L)));

        // null
        System.out.println(gson.toJson(null).equals(myGson.toJson(null)));

        // char
        System.out.println(gson.toJson('a').equals(myGson.toJson('a')));

        // String
        System.out.println(gson.toJson("aaa").equals(myGson.toJson("aaa")));

        // int[]
        System.out.println(gson.toJson(new int[]{1, 2, 3}).equals(myGson.toJson(new int[]{1, 2, 3})));

        // List
        System.out.println(gson.toJson(List.of(1, 2, 3)).equals(myGson.toJson(List.of(1, 2, 3))));

        // Collections
        System.out.println(gson.toJson(Collections.singletonList(1)).equals(myGson.toJson(Collections.singletonList(1))));

    }

    private static ArrayList<String> collectionFactory() {
        ArrayList<String> collection = new ArrayList<>();
        collection.add("Great");
        collection.add("Bad");

        return collection;
    }

    private static String[] stringArrayFactory() {
        return new String[]{"Fox", "Wolf", "Monkey"};
    }

    private static int[] integerArrayFactory() {
        return new int[]{1, 2, 3};
    }
}
