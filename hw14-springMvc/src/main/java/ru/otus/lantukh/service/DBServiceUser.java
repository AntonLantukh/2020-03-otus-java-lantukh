package ru.otus.lantukh.service;

import ru.otus.lantukh.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsers();
}
