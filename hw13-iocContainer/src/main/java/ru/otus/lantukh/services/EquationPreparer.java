package ru.otus.lantukh.services;

import ru.otus.lantukh.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
