package ru.otus.lantukh.atm;

import jdk.jfr.Description;

import java.util.HashMap;

public class Atm {
    private Dispenser dispenser;
    private HashMap<Integer, Integer> depositValue;
    private int withdrawValue;

    public Atm() {
        this.dispenser = new Dispenser();
    }

    public void withdrawCash(int sum) {
        this.withdrawValue = sum;
        executeCommand(new WithdrawCommand(dispenser, this));
    }

    public void depositCash(HashMap<Integer, Integer> sum) {
        this.depositValue = sum;
        executeCommand(new DepositCommand(dispenser, this));
    }

    public HashMap<Integer, Integer> getDepositValue() {
        return depositValue;
    }

    public int getWithdrawValue() {
        return withdrawValue;
    }

    private void executeCommand(Command command) {
        command.execute();
    }

    @Description("Считаем сумму по мапе <Номинал, Количество>")
    public int countSumFromMap(HashMap<Integer, Integer> amount) {
        return amount
                .entrySet()
                .stream()
                .mapToInt((item) -> item.getKey() * item.getValue())
                .sum();
    }
}
