package ru.otus.lantukh.atm;

public class CashCell {
    private int count;

    public CashCell(int count) {
        this.count = count;
    }

    public CashCell() {
        this.count = 1000;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count + this.count;
    }

    public boolean isAvailable(int requestedCount) {
        return count >= requestedCount;
    }
}
