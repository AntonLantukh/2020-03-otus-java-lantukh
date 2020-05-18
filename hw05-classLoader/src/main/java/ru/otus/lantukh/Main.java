package ru.otus.lantukh;

import ru.otus.lantukh.calculation.CalculationInterface;
import ru.otus.lantukh.calculation.CalculationProxy;

public class Main {
    public static void main(String[] args) {
        CalculationInterface calculationProxy = new CalculationProxy().getProxy();

        calculationProxy.calculate(10, 20);
        calculationProxy.calculate(50, 20, 10);
    }
}
