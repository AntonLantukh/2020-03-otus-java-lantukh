package ru.otus.lantukh.core.service;

import ru.otus.lantukh.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    long saveAccount(Account account);

    Optional<Account> getAccount(long id);
}
