package ru.otus.lantukh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    private static class Animal {

    }

    private static class Cat extends Animal {
        private String name;

        Cat(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return "Cat{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static class CatsComparator implements Comparator<Cat> {
        @Override
        public int compare(Cat o1, Cat o2) {
            return o1.getName().compareTo(o2.getName());
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    }

    public static void main(String[] args) {
        Cat almaCat = new Cat("Alma");
        Cat murkaCat = new Cat("Murka");
        Cat emmaCat = new Cat("Emma");

        Cat dollyCat = new Cat("Dolly");
        Cat rubyCat = new Cat("Ruby");
        Cat marussiaCat = new Cat("Marussia");

        Cat greatCat = new Cat("Great");
        Cat superCat = new Cat("Super");
        Cat nyanCat = new Cat("Nyan");


        DIYArrayList<Cat> myArray = new DIYArrayList<>();
        myArray.add(almaCat);
        myArray.add(murkaCat);
        myArray.add(emmaCat);

        Cat[] cats1 = {dollyCat, rubyCat, marussiaCat};

        ArrayList<Cat> cats2 = new ArrayList<>();
        cats2.add(greatCat);
        cats2.add(superCat);
        cats2.add(nyanCat);

        Collections.addAll(myArray, cats1);
        Collections.copy(myArray, cats2);
        Collections.sort(myArray, new CatsComparator());

        for (int i = 0; i < myArray.size(); i++) {
            System.out.println(myArray.get(i).toString());
        }
    }
}
