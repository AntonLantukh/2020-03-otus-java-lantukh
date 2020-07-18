package ru.otus.lantukh.department;

import ru.otus.lantukh.atm.Atm;
import ru.otus.lantukh.listener.EventBalanceListener;
import ru.otus.lantukh.publisher.Event;
import ru.otus.lantukh.publisher.EventManager;

import java.util.ArrayList;

public class Department {
    private final EventManager eventManager;
    private final ArrayList<Atm> children = new ArrayList<Atm>();
    private int balance;

    public void add(Atm atm) {
        children.add(atm);
        atm.department = this;
    }

    public void subscribeBalance() {
        for (Atm atm: children) {
            eventManager.subscribe(Event.BALANCE, new EventBalanceListener(atm));
        }
    }

    public void subscribeReinitialize() {
        for (Atm atm: children) {
            eventManager.subscribe(Event.REINITIALIZE, new EventBalanceListener(atm));
        }
    }

    public Department() {
        this.eventManager = new EventManager(Event.values());
    }

    public int getBalance() {
        eventManager.notify(Event.BALANCE);

        return balance;
    }

    public void updateBalance(int sum) {
        this.balance = balance + sum;
    }

    public void resetAtms() {
        eventManager.notify(Event.REINITIALIZE);
    }
}
