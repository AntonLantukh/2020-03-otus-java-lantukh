package ru.otus.lantukh.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception ex) {
        super(ex);
    }
}
