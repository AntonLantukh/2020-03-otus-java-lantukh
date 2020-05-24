package ru.otus.lantukh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.otus.lantukh.atm.Atm;
import ru.otus.lantukh.atm.account.Account;

public class Main {
    public static void main(String[] args) throws IOException {
        int INITIAL_BALANCE = 100_000;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hello! Please type your name");
        String name = reader.readLine();

        System.out.println(name + ", please type operation you would like to perform: 'DEPOSIT' / 'WITHDRAW'");
        String operation = reader.readLine();

        var account = new Account(name, INITIAL_BALANCE);
        Atm atm = new Atm(account);

        switch (operation) {
            case "DEPOSIT":
                System.out.println(name + ", please insert the money. As example: 100:1,50:2");
                String depositSum = reader.readLine();
                atm.depositCash(depositSum);
                break;
            case "WITHDRAW":
                System.out.println(name + ", please type sum of money you would like to withdraw");
                int withdrawSum = Integer.parseInt(reader.readLine());
                atm.withdrawCash(withdrawSum);
                break;
            default:
                throw new RuntimeException("Invalid operation");
        };
    }
}
