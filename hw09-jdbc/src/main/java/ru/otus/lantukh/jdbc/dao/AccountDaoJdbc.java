package ru.otus.lantukh.jdbc.dao;

import ru.otus.lantukh.core.dao.AccountDao;
import ru.otus.lantukh.core.model.Account;
import ru.otus.lantukh.core.sessionmanager.SessionManager;
import ru.otus.lantukh.jdbc.mapper.JdbcMapperImpl;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private final JdbcMapperImpl<Account> jdbcMapper;

    public AccountDaoJdbc(JdbcMapperImpl<Account> jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<Account> findByNo(long no) {
        return jdbcMapper.findById(no);
    }

    @Override
    public long insertAccount(Account account) {
        return jdbcMapper.insert(account);
    }

    @Override
    public long updateAccount(Account account) {
        return jdbcMapper.update(account);
    }

    @Override
    public long insertOrUpdate(Account account) {
        return jdbcMapper.insertOrUpdate(account);
    }

    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
