package ru.otus.lantukh.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lantukh.core.dao.AccountDao;
import ru.otus.lantukh.core.model.Account;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

    private final AccountDao accountDao;

    public DbServiceAccountImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public long saveAccount(Account account) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var accountId = accountDao.insertOrUpdate(account);
                sessionManager.commitSession();
                if (account.getNo() == 0L) {
                    account.setNo(accountId);
                    logger.info("saved account: {}", accountId);
                } else {
                    logger.info("updated account: {}", accountId);
                }

                return accountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Account> getAccount(long id) {
        try (var sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = accountDao.findByNo(id);

                logger.info("account: {}", accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
