package ru.otus.lantukh.core.model;

import java.math.BigInteger;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class Account {
    @Id
    private long no;
    private final String type;
    private final int rest;

    public Account(String type, int rest) {
        this.type = type;
        this.rest = rest;
    }

    public Account(long no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public int getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
