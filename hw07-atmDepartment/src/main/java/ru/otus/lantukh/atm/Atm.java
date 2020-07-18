package ru.otus.lantukh.atm;

import ru.otus.lantukh.department.Department;

import java.util.HashMap;
import java.util.Map;

public class Atm {
    private final Dispenser dispenser;
    public Department department;

    public Atm() {
        this.dispenser = new Dispenser();
    }

    public Map<Integer, Integer> withdrawCash(int sum) {
        Map<Integer, Integer> amount = dispenser.withdrawCash(sum);

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
