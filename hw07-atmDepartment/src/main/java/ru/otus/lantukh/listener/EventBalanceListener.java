package ru.otus.lantukh.listener;

import ru.otus.lantukh.atm.*;

public class EventBalanceListener implements EventListener {
    private Atm atm;

    public EventBalanceListener(Atm atm) {
        this.atm = atm;
    }

    public void performOperation() {
        int balance = atm.getBalance();
        atm.department.updateBalance(balance);
    };
}
