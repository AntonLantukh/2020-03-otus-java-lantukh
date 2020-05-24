package ru.otus.lantukh.atm.dispenseMechanism;

public class DispenseException extends RuntimeException {
    DispenseException() {
        super("ATM can't dispense requested sum. Not enough cash inside");
    }
}
