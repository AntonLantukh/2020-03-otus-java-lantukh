package ru.otus.lantukh.atm.dispenseMechanism;

import jdk.nashorn.api.scripting.NashornException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DispensingMechanismTest {
    @DisplayName("dispenseCash выдает запрошенную сумму минимальным количеством банкнот (1)")
    @Test
    void shouldCorrectlyDispenseRequestedSumFirstCase() {
        HashMap<Integer, Integer> dispensedCash = new HashMap<>();
        dispensedCash.put(100, 12);
        dispensedCash.put(50, 1);
        dispensedCash.put(10, 1);
        dispensedCash.put(1, 3);

        var dispensingMechanism = new DispensingMechanism();
        assertEquals(dispensingMechanism.dispenseCash(1263), dispensedCash);
    }

    @DisplayName("dispenseCash выдает запрошенную сумму минимальным количеством банкнот (2)")
    @Test
    void shouldCorrectlyDispenseRequestedSumSecondCase() {
        HashMap<Integer, Integer> dispensedCash = new HashMap<>();
        dispensedCash.put(100, 31);
        dispensedCash.put(50, 1);
        dispensedCash.put(20, 1);
        dispensedCash.put(5, 1);
        dispensedCash.put(1, 3);

        var dispensingMechanism = new DispensingMechanism();
        assertEquals(dispensingMechanism.dispenseCash(3178), dispensedCash);
    }

    @DisplayName("dispenseCash, если запрошенной суммы нет -> кидает исключение")
    @Test
    void shouldThrowWhenThereIsoRequestedSum() {
        var dispensingMechanism = new DispensingMechanism();
        assertThrows(DispenseException.class, () -> dispensingMechanism.dispenseCash(1000000));
    }

    @DisplayName("depositCash, не должен бросать исключение при зачислении средств")
    @Test
    void shouldNotThrowWhenDepositingCash() {
        HashMap<Integer, Integer> depositSum = new HashMap<>();
        var dispensingMechanism = new DispensingMechanism();
        dispensingMechanism.depositCash(depositSum);
    }
}