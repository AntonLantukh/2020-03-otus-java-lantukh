package ru.otus.lantukh.atm;

import java.util.HashMap;

public class WithdrawCommand extends Command {
    public WithdrawCommand(Dispenser dispenser, Atm atm) {
        super(dispenser, atm);
    }

    public void execute() {
        int amount = atm.getWithdrawValue();
        HashMap<Integer, Integer> amountMap = dispenser.withdrawCash(amount);

        System.out.println(amount +  " dollars " + "were withdrawn: " + amountMap.toString());
    }
}
