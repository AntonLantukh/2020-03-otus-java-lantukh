package ru.otus.lantukh.atm.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {
    @DisplayName("getBalance возаращает текущий баланс")
    @Test
    void shouldReturnCurrentBalance() {
        var account = new Account("Test", 1000);
        int balance = account.getBalance();

        assertEquals(balance, 1000);
    }

    @DisplayName("depositCash увеличивает текущий баланс на указанную сумму")
    @Test
    void shouldCorrectlyIncreaseBalance() {
        var account = new Account("Test", 1000);
        account.depositCash(1500);
        int balance = account.getBalance();

        assertEquals(balance, 2500);
    }

    @DisplayName("withdrawCash уменьшает текущий баланс на указанную сумму")
    @Test
    void shouldCorrectlyDecreaseBalance() {
        var account = new Account("Test", 1000);
        account.withdrawCash(500);
        int balance = account.getBalance();

        assertEquals(balance, 500);
    }

    @DisplayName("withdrawCash выбрасывает ошибку, если сумма списания больше, чем есть на балансе")
    @Test
    void shouldThrowWhenThereIsNotEnoughBalance() {
        var account = new Account("Test", 1000);
        assertThrows(AccountException.class, () -> account.withdrawCash(1500));
    }
}