package ru.otus.lantukh.atm.dispenseMechanism;

import java.util.HashMap;

public class DispensingMechanism {
    private int[] CELLS = {100, 50, 20, 10, 5, 1};
    private int BANKNOTE_COUNT = 1000;
    private HashMap<Integer, CashCell> vault;

    public DispensingMechanism() {
        HashMap<Integer, CashCell> newVault = new HashMap<>();

        for(int cell: CELLS) {
            newVault.put(cell, new CashCell(BANKNOTE_COUNT));
        }

        this.vault = newVault;
    }

    private boolean isCashCellAvailable(int requestedCount, int nominal) {
        CashCell cell = vault.get(nominal);
        int count = cell.getCount();

        return count >= requestedCount;
    }

    public void depositCash(HashMap<Integer, Integer> amount) {
        for (HashMap.Entry<Integer, Integer> entry : amount.entrySet()) {
            Integer nominal = entry.getKey();
            Integer count = entry.getValue();

            CashCell cell = vault.get(nominal);
            cell.setCount(count);
        }
    }

    public HashMap<Integer, Integer> dispenseCash(int amount) {
        HashMap<Integer, Integer> dispensedCash = new HashMap<>(CELLS.length);
        for (int cell : CELLS) {
            int count = amount / cell;
            int rest = amount % cell;

            // Дополнительно проверяем, что деньги в ячейке есть
            if (count > 0 && isCashCellAvailable(count, cell)) {
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
