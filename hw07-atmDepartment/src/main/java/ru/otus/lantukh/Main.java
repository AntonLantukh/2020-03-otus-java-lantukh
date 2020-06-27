package ru.otus.lantukh;

import ru.otus.lantukh.atm.Atm;
import ru.otus.lantukh.department.Department;

public class Main {
    public static void main(String[] args) {
        Atm atmFirst = new Atm();
        Atm atmSecond = new Atm();
        Department department = new Department();

        department.add(atmFirst);
        department.add(atmSecond);

        department.subscribeBalance();
        department.subscribeReinitialize();

        department.getBalance();

        atmSecond.withdrawCash(10000);
        atmFirst.withdrawCash(15000);

        department.resetAtms();
    }
}
