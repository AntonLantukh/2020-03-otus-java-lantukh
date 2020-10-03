package ru.otus.lantukh.core.dao;

import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
