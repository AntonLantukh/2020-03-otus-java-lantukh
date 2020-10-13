package ru.otus.lantukh.services;

public class UserAuthServiceImpl implements UserAuthService {
    private final static String ADMIN_LOGIN = "admin";
    private final static String ADMIN_PASSWORD = "12345";

    @Override
    public boolean authenticate(String login, String password) {
        return ADMIN_LOGIN.equals(login) && ADMIN_PASSWORD.equals(password);
    }

}
