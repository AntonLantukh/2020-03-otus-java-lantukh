package ru.otus.lantukh.atm;

import jdk.jfr.Description;
import ru.otus.lantukh.atm.account.Account;
import ru.otus.lantukh.atm.dispenseMechanism.DispensingMechanism;

import java.util.HashMap;

public class Atm {
    private Account account;
    private DispensingMechanism dispensingMechanism;

    public Atm(Account account) {
        this.account = account;
        this.dispensingMechanism = new DispensingMechanism();
    }

    public void withdrawCash(int sum) {
        account.withdrawCash(sum);
        HashMap<Integer, Integer> amountMap = dispensingMechanism.dispenseCash(sum);

        System.out.println(sum +  " dollars " + "were withdrawn: " + amountMap.toString());
        System.out.println("The rest is " + account.getBalance());

    }

    public void depositCash(String sum) {
        HashMap<Integer, Integer> amount = generateAmountMap(sum);
        int parsedSum = countSumFromMap(amount);

        dispensingMechanism.depositCash(amount);
        account.depositCash(parsedSum);
        System.out.println(parsedSum +  " dollars " + "were deposited.");
        System.out.println("The rest is " + account.getBalance());
    }

    @Description("Делаем мапу из консольной стринги")
    private HashMap<Integer, Integer> generateAmountMap(String sum) {
        HashMap<Integer, Integer> amount = new HashMap<>();

        for (String cell : sum.split(",")) {
            String[] value = cell.split(":");
            amount.put(Integer.parseInt(value[0]), Integer.parseInt(value[1]));
        }

        return amount;
    }

    @Description("Считаем сумму по мапе <Номинал, Количество>")
    private int countSumFromMap(HashMap<Integer, Integer> amount) {
        return amount
                .entrySet()
                .stream()
                .mapToInt((item) -> item.getKey() * item.getValue())
                .sum();
    }
}
