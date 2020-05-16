package ru.otus.lantukh.calculation;

public class CalculationImpl implements CalculationInterface {
    @Override
    public void calculate(int a, int b) {
        int sum = a + b;
        System.out.println("Result: " + sum);
    };
}
