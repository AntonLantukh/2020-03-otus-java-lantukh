package ru.otus.lantukh.jdbc.dao;

import ru.otus.lantukh.core.dao.AccountDao;
import ru.otus.lantukh.core.model.Account;
import ru.otus.lantukh.core.sessionmanager.SessionManager;
import ru.otus.lantukh.jdbc.mapper.JdbcMapper;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private final JdbcMapper<Account> jdbcMapper;

    public AccountDaoJdbc(JdbcMapper<Account> jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<Account> findByNo(long no) {
        return jdbcMapper.findById(no);
    }

    @Override
    public long insertAccount(Account account) {
        long accountId = jdbcMapper.insert(account);
        account.setNo(accountId);

        return accountId;
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
