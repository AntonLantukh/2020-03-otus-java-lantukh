package ru.otus.lantukh.jdbc.dao;

import java.util.Optional;

import ru.otus.lantukh.core.dao.UserDao;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.core.sessionmanager.SessionManager;
import ru.otus.lantukh.jdbc.mapper.JdbcMapper;

public class UserDaoJdbc implements UserDao {
    private final JdbcMapper<User> jdbcMapper;

    public UserDaoJdbc(JdbcMapper<User> jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcMapper.findById(id);
    }

    @Override
    public long insertUser(User user) {
        long userId = jdbcMapper.insert(user);
        user.setId(userId);

        return userId;
    }

    @Override
    public long updateUser(User user) {
        return jdbcMapper.update(user);
    }

    @Override
    public long insertOrUpdate(User user) {
        return jdbcMapper.insertOrUpdate(user);
    }

    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
