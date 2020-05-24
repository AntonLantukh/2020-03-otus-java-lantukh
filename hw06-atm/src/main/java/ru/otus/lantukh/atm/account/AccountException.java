package ru.otus.lantukh.atm.account;

public class AccountException extends RuntimeException {
    AccountException() {
        super("ATM can't dispense requested sum. You don't have sum on your account");
    }
}
