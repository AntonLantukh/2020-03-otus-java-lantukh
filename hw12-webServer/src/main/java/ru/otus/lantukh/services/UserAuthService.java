package ru.otus.lantukh.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
