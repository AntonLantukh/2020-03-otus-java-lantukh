package ru.otus.lantukh.atm;

import jdk.jfr.Description;

import java.util.HashMap;

public class Atm {
    private Dispenser dispenser;

    public Atm() {
        this.dispenser = new Dispenser();
    }

    public HashMap<Integer, Integer> withdrawCash(int sum) {
        HashMap<Integer, Integer> amount = dispenser.withdrawCash(sum);
        System.out.println(sum +  " dollars " + "were withdrawn: " + amount.toString());

        return amount;
    }

    public void depositCash(HashMap<Integer, Integer> sum) {
        dispenser.depositCash(sum);
        System.out.println(countSumFromMap(sum) +  " dollars " + "were deposited.");
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
