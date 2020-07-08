package ru.otus.lantukh;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MyGson myGson = new MyGson();
        Animal animal = new Animal(
                12,
                20,
                "wolf",
                stringArrayFactory(),
                integerArrayFactory(),
                collectionFactory()
        );
        String json = myGson.toJson(animal);

        Gson gson = new Gson();
        Animal animal2 = gson.fromJson(json, Animal.class);
        System.out.println(animal2.toString());
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
