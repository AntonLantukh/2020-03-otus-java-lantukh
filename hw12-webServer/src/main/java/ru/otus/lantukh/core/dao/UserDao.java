package ru.otus.lantukh.core.dao;

import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    List<User> findUsers();

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
