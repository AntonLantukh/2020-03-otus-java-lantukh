package ru.otus.lantukh;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lantukh.core.model.Account;
import ru.otus.lantukh.core.service.DbServiceAccountImpl;
import ru.otus.lantukh.jdbc.dao.AccountDaoJdbc;
import ru.otus.lantukh.jdbc.dao.UserDaoJdbc;
import ru.otus.lantukh.core.service.DbServiceUserImpl;
import ru.otus.lantukh.jdbc.DbExecutorImpl;
import ru.otus.lantukh.h2.DataSourceH2;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.jdbc.mapper.JdbcMapperImpl;
import ru.otus.lantukh.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();
        var sessionManager = new SessionManagerJdbc(dataSource);

        demo.createUserTable(dataSource);
        performUserOperations(sessionManager);

        demo.createAccountTable(dataSource);
        performAccountOperations(sessionManager);
    }

    private static void performUserOperations(SessionManagerJdbc sessionManager) throws SQLException {
        DbExecutorImpl<User> dbUserExecutor = new DbExecutorImpl<>();
        JdbcMapperImpl<User> jdbcUserMapper = new JdbcMapperImpl<>(User.class, sessionManager, dbUserExecutor);

        var userDao = new UserDaoJdbc(jdbcUserMapper);
        var dbServiceUser = new DbServiceUserImpl(userDao);

        var idUser1 = dbServiceUser.saveUser(new User(0, "dbServiceUser", 30));
        var idUser2 = dbServiceUser.saveUser(new User(50, "dbServiceUser50", 31));
        // Обновляем user
        dbServiceUser.saveUser(new User(50, "dbServiceUser60", 31));

        Optional<User> user1 = dbServiceUser.getUser(idUser1);
        Optional<User> user2 = dbServiceUser.getUser(idUser2);

        user1.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        user2.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );
    }

    private static void performAccountOperations(SessionManagerJdbc sessionManager) {
        DbExecutorImpl<Account> dbAccountExecutor = new DbExecutorImpl<>();
        JdbcMapperImpl<Account> jdbcAccountMapper = new JdbcMapperImpl<>(Account.class, sessionManager, dbAccountExecutor);

        var accountDao = new AccountDaoJdbc(jdbcAccountMapper);
        var dbServiceAccount = new DbServiceAccountImpl(accountDao);

        var idAccount1 = dbServiceAccount.saveAccount(new Account(121212, "new", 300));
        var idAccount2 = dbServiceAccount.saveAccount(new Account(32323, "old", 100));
        // Обновляем account
        dbServiceAccount.saveAccount(new Account(121212, "new", 700));

        Optional<Account> account1 = dbServiceAccount.getAccount(idAccount1);
        Optional<Account> account2 = dbServiceAccount.getAccount(idAccount2);

        account1.ifPresentOrElse(
                crAccount -> logger.info("created account, no:{}", crAccount.getNo()),
                () -> logger.info("account was not created")
        );

        account2.ifPresentOrElse(
                crAccount -> logger.info("created account, no:{}", crAccount.getNo()),
                () -> logger.info("account was not created")
        );
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("CREATE TABLE user(id LONG AUTO_INCREMENT, name VARCHAR(50), age INT)")) {
            pst.executeUpdate();
        }
        System.out.println("table user created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("CREATE TABLE account(no BIGINT(20) AUTO_INCREMENT, type VARCHAR(255), rest INT)")) {
            pst.executeUpdate();
        }
        System.out.println("table account created");
    }
}
