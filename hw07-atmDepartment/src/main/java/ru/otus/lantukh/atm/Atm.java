package ru.otus.lantukh.atm;

import ru.otus.lantukh.department.Department;

import java.util.HashMap;

public class Atm {
    private final Dispenser dispenser;
    public Department department;

    public Atm() {
        this.dispenser = new Dispenser();
    }

    public HashMap<Integer, Integer> withdrawCash(int sum) {
        HashMap<Integer, Integer> amount = dispenser.withdrawCash(sum);

        return amount;
    }

    public void depositCash(HashMap<Integer, Integer> sum) {
        dispenser.depositCash(sum);
    }

    public int getBalance() {
        return dispenser.getBalance();
    }

    public void reinitialize() {
        dispenser.initialize();
    }
}
