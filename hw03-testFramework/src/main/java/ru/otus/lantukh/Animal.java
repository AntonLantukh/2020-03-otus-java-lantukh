package ru.otus.lantukh;

public class Animal {
    String name;
    String sound;

    Animal(String name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

    public String getName() {
        return name;
    }
}