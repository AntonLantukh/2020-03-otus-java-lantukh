package ru.otus.lantukh;

import java.util.HashMap;

import ru.otus.lantukh.atm.Atm;

public class Main {
    public static void main(String[] args) {
        Atm atm = new Atm();

        // Deposit
        HashMap<Integer, Integer> depositSum = new HashMap<>();
        depositSum.put(100, 5);
        depositSum.put(20, 1);
        depositSum.put(5, 4);

        atm.depositCash(depositSum);

        // Withdraw
        int withdrawSum = 1340;
        atm.withdrawCash(withdrawSum);
    }
}
