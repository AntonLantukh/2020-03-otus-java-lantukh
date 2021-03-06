package ru.otus.lantukh;

import java.util.ArrayList;

public class Benchmark implements BenchmarkMBean {
    private volatile int pauseSizeTime = 0;

    public void run() throws InterruptedException {
        ArrayList<Animal> list = new ArrayList<>();

        for (int j = 0; ; j++) {
            Animal animal = new Animal();
            animal.setName("Wolf");
            animal.setSound("Waugh!");
            list.add(animal);

            if (j % pauseSizeTime == 0) {
                Thread.sleep(40);
                list.subList(0, j / pauseSizeTime);
            }
        }
    };

    @Override
    public int getPauseSize() {
        return pauseSizeTime;
    }

    @Override
    public void setPauseSize(int size) {
        this.pauseSizeTime = size;
    }
}
