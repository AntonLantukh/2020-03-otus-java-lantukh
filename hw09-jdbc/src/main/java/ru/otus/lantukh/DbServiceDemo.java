package ru.otus.lantukh;

import java.sql.SQLException;
import javax.sql.DataSource;

import ru.otus.lantukh.core.model.Account;
import ru.otus.lantukh.core.service.DbServiceAccountImpl;
import ru.otus.lantukh.jdbc.dao.AccountDaoJdbc;
import ru.otus.lantukh.jdbc.dao.UserDaoJdbc;
import ru.otus.lantukh.core.service.DbServiceUserImpl;
import ru.otus.lantukh.jdbc.DbExecutor;
import ru.otus.lantukh.jdbc.DbExecutorImpl;
import ru.otus.lantukh.h2.DataSourceH2;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.jdbc.mapper.JdbcMapper;
import ru.otus.lantukh.jdbc.mapper.JdbcMapperImpl;
import ru.otus.lantukh.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
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
        DbExecutor<User> dbUserExecutor = new DbExecutorImpl<>();
        JdbcMapper<User> jdbcUserMapper = new JdbcMapperImpl<>(User.class, sessionManager, dbUserExecutor);

        var userDao = new UserDaoJdbc(jdbcUserMapper);
        var dbServiceUser = new DbServiceUserImpl(userDao);

        var idUser1 = dbServiceUser.saveUser(new User(0L, "dbServiceUser", 30));
        var idUser2 = dbServiceUser.saveUser(new User( 0L,"dbServiceUser50", 31));
        // Обновляем user
        dbServiceUser.saveUser(new User(idUser2,"dbServiceUser60", 31));
    }

    private static void performAccountOperations(SessionManagerJdbc sessionManager) {
        DbExecutor<Account> dbAccountExecutor = new DbExecutorImpl<>();
        JdbcMapper<Account> jdbcAccountMapper = new JdbcMapperImpl<>(Account.class, sessionManager, dbAccountExecutor);

        var accountDao = new AccountDaoJdbc(jdbcAccountMapper);
        var dbServiceAccount = new DbServiceAccountImpl(accountDao);

        var idAccount1 = dbServiceAccount.saveAccount(new Account(0L, "new", 300));
        var idAccount2 = dbServiceAccount.saveAccount(new Account( 0L, "old", 100));
        // Обновляем account
        dbServiceAccount.saveAccount(new Account(idAccount2, "new", 700));
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("CREATE TABLE user(id LONG NOT NULL AUTO_INCREMENT, name VARCHAR(50), age INT)")) {
            pst.executeUpdate();
        }
        System.out.println("table user created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("CREATE TABLE account(no BIGINT(20) NOT NULL AUTO_INCREMENT, type VARCHAR(255), rest INT)")) {
            pst.executeUpdate();
        }
        System.out.println("table account created");
    }
}
