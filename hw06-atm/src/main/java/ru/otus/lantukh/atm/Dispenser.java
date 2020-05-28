package ru.otus.lantukh.atm;

import java.util.HashMap;
import java.util.List;

public class Dispenser {
    private List<Integer> nominalValues;
    private HashMap<Integer, CashCell> vault;

    public Dispenser() {
        List<Integer> nominalValues = Nominal.getNominals();
        HashMap<Integer, CashCell> newVault = new HashMap<>();

        for(int cell: nominalValues) {
            newVault.put(cell, new CashCell());
        }

        this.vault = newVault;
        this.nominalValues = nominalValues;
    }

    HashMap<Integer, CashCell> getVault() {
        return vault;
    }

    public CashCell getCashCell(int nominal) {
        return getVault().get(nominal);
    }

    public void updateCashCell(int nominal, int count) {
        CashCell cell = getCashCell(nominal);
        cell.setCount(count);
    }

    public void depositCash (HashMap<Integer, Integer> amount) {
        for (HashMap.Entry<Integer, Integer> entry : amount.entrySet()) {
            Integer nominal = entry.getKey();
            Integer count = entry.getValue();

            updateCashCell(nominal, count);
        }
    }

    public HashMap<Integer, Integer> withdrawCash(int amount) {
        HashMap<Integer, Integer> dispensedCash = new HashMap<>(nominalValues.size());
        for (int cell : nominalValues) {
            int count = amount / cell;
            int rest = amount % cell;

            // Дополнительно проверяем, что деньги в ячейке есть
            if (count > 0 && getCashCell(cell).isAvailable(count)) {
                dispensedCash.put(cell, count);
                amount = rest;
            }
        }
        // Не хватило денег на выдачу
        if (amount != 0) {
            throw new DispenseException();
        }

        return dispensedCash;
    }
}
