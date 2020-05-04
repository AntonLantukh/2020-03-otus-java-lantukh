package ru.otus.lantukh;

import ru.otus.lantukh.tester.Before;
import ru.otus.lantukh.tester.Test;
import static ru.otus.lantukh.tester.Assertions.*;

public class AnimalTest {
    Animal cat;
    Animal dog;

    @Before
    public void getAnimals() {
        this.cat = new Animal("cat", "Meow!");
        this.dog = new Animal("dog", "Bark!");
    }

    @Test
    public void checkAnimalSound() {
        String sound = cat.getSound();
        assertEquals("Meow!", sound);
    }

    @Test
    public void checkAnimalName() {
        String name = dog.getName();
        assertTrue(name.equals("ddog"));
    }
}