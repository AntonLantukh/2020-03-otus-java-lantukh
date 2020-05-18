package ru.otus.lantukh.calculation;

import ru.otus.lantukh.log.Log;

public class CalculationImpl implements CalculationInterface {
    @Override
    @Log
    public void calculate(int a, int b) {
        int sum = a + b;
        System.out.println("Result: " + sum);
    };

    @Override
    public void calculate(int a, int b, int c) {
        int sum = a + b + c;
        System.out.println("Result: " + sum);
    };
}
