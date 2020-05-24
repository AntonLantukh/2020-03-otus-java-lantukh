package ru.otus.lantukh.atm.account;

public class Account {
    private String name;
    private int balance;

    public Account(String name, int balance) {
        this.balance = balance;
        this.name = name;
    }

    public int getBalance () {
        return balance;
    }

    public void depositCash (int sum) {
        balance = balance + sum;
    }

    public void withdrawCash (int sum) {
        int newBalanceValue = balance - sum;

        if (newBalanceValue < 0) {
            // Если не хватает средств, не даем снять
            throw new AccountException();
        } else {
            balance = newBalanceValue;
        }
    }

    public String getName () {
        return name;
    }
}
