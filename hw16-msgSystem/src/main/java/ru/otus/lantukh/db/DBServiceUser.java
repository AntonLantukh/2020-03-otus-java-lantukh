package ru.otus.lantukh.db;

import java.util.List;
import java.util.Optional;

import ru.otus.lantukh.model.User;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsers();
}
