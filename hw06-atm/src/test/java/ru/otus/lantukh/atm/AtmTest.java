package ru.otus.lantukh.atm;

import ru.otus.lantukh.atm.account.Account;
import ru.otus.lantukh.atm.account.AccountException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AtmTest {
    private Account account;
    private PrintStream outMock;

    @BeforeEach
    void initializeAccount() {
        this.outMock = mock(PrintStream.class);
        System.setOut(outMock);
        this.account = new Account("Test", 10000);
    }

    @DisplayName("withdrawCash корректно выводит сумму на выдачу и актуальный остаток (1250)")
    @Test
    void shouldCalculateWithdrawnCash() {
        var atm = new Atm(account);
        atm.withdrawCash(1250);

        verify(outMock, times(1)).println("1250 dollars were withdrawn: {50=1, 100=12}");
        verify(outMock, times(1)).println("The rest is 8750");
    }

    @DisplayName("withdrawCash выбрасывает ошибку, если средств не хватает")
    @Test
    void shouldThrowIfThereIsNotEnoghCash() {
        var atm = new Atm(account);
        assertThrows(AccountException.class, () -> atm.withdrawCash(12000));
    }

    @DisplayName("depositCash корректно выводит сумму пополнения и актуальный остаток")
    @Test
    void shouldCalculateDepositCash() {
        var atm = new Atm(account);
        atm.depositCash("100:1,50:23");

        verify(outMock, times(1)).println("1250 dollars were deposited.");
        verify(outMock, times(1)).println("The rest is 11250");
    }
}