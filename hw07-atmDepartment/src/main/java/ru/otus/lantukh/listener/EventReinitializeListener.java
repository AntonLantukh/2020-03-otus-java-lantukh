package ru.otus.lantukh.listener;

import ru.otus.lantukh.atm.Atm;

public class EventReinitializeListener implements EventListener {
    private Atm atm;

    public EventReinitializeListener(Atm atm) {
        this.atm = atm;
    }

    public void performOperation() {
        atm.reinitialize();
    };
}
