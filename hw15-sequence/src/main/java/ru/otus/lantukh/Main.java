package ru.otus.lantukh;

import java.util.concurrent.TimeUnit;

public class Main {
    private boolean ASC = true;
    private final Object monitor = new Object();
    private String last;

    public static void main(String[] args) {
        Main main = new Main();
        Thread thread1 = new Thread(main::doTask);
        Thread thread2 = new Thread(main::doTask);

        thread1.start();
        thread2.start();
    }

    private int count(int number) {
        int MAX_INT = 10;
        int MIN_INT = 1;
        int returnValue;

        if (number == MAX_INT) {
            ASC = false;
        } else if (number == MIN_INT && !ASC) {
            Thread.currentThread().interrupt();
        }

        System.out.println(Thread.currentThread().getName() + " | current: " + number);
        returnValue = ASC ? number + 1 : number - 1;
        last = Thread.currentThread().getName();

        return returnValue;
    }


    private void doTask() {
        int counter = 0;

        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    while (Thread.currentThread().getName().equals(last)) {
                        monitor.wait();
                    }

                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                    counter = count(counter);
                    monitor.notify();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}