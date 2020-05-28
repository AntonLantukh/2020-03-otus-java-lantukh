package ru.otus.lantukh.atm;

abstract class Command {
    protected Dispenser dispenser;
    protected Atm atm;

    public Command(Dispenser dispenser, Atm atm) {
        this.dispenser = dispenser;
        this.atm = atm;
    }

    public abstract void execute();
}
