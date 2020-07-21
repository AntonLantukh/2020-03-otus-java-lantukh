package ru.otus.lantukh.jdbc.dao;

import java.math.BigInteger;
import java.util.Optional;

import ru.otus.lantukh.core.dao.UserDao;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.core.sessionmanager.SessionManager;
import ru.otus.lantukh.jdbc.mapper.JdbcMapperImpl;

public class UserDaoJdbc implements UserDao {
    private final JdbcMapperImpl<User> jdbcMapper;

    public UserDaoJdbc(JdbcMapperImpl<User> jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcMapper.findById(id);
    }

    @Override
    public long insertUser(User user) {
        return jdbcMapper.insert(user);
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
