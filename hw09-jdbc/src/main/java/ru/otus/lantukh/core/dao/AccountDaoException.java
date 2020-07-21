package ru.otus.lantukh.core.dao;

public class AccountDaoException extends RuntimeException {
    public AccountDaoException(Exception ex) {
        super(ex);
    }
}
