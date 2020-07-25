package ru.otus.lantukh.jdbc.mapper;

import ru.otus.lantukh.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface JdbcMapper<T> {
    long insert(T objectData);

    long update(T objectData);

    long insertOrUpdate(T objectData);

    Optional<T> findById(long id);

    public SessionManager getSessionManager();
}
