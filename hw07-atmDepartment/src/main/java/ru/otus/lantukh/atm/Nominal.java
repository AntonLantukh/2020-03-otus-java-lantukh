package ru.otus.lantukh.atm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Nominal {
    HUNDRED(100),

    FIFTY(50),

    TWENTY(20),

    TEN(10),

    FIVE(5),

    TWO(2),

    ONE(1);

    private final int value;

    private Nominal (int value) {
        this.value = value;
    }

    private int getValue () {
        return value;
    }

    public static List<Integer> getNominals() {
        Nominal[] nominalValues = Nominal.values();

        return Arrays
                .stream(nominalValues)
                .map(Nominal::getValue)
                .collect(Collectors.toList());
    }
}
