package ru.otus.lantukh.core.dao;

import ru.otus.lantukh.core.model.Account;
import ru.otus.lantukh.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findByNo(long no);

    long insertAccount(Account account);

    long updateAccount(Account account);

    long insertOrUpdate(Account account);

    SessionManager getSessionManager();
}
