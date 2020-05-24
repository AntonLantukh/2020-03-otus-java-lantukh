package ru.otus.lantukh.atm.dispenseMechanism;

public class CashCell {
    private int count;

    public CashCell(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count + this.count;
    }
}
