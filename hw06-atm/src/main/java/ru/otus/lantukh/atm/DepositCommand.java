package ru.otus.lantukh.atm;

import java.util.HashMap;

public class DepositCommand extends Command {
    public DepositCommand(Dispenser dispenser, Atm atm) {
        super(dispenser, atm);
    }

    public void execute() {
        HashMap<Integer, Integer> amount = atm.getDepositValue();
        dispenser.depositCash(amount);

        System.out.println(atm.countSumFromMap(amount) +  " dollars " + "were deposited.");
    }
}
