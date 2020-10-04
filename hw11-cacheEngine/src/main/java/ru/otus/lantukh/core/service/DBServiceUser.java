package ru.otus.lantukh.core.service;

import ru.otus.lantukh.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

}
