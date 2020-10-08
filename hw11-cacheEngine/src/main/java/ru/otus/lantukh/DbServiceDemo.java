package ru.otus.lantukh;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lantukh.cache.HwCache;
import ru.otus.lantukh.cache.MyCache;
import ru.otus.lantukh.core.dao.UserDao;
import ru.otus.lantukh.core.model.AddressDataSet;
import ru.otus.lantukh.core.model.PhoneDataSet;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.core.service.DBServiceUser;
import ru.otus.lantukh.core.service.DbServiceUserImpl;
import ru.otus.lantukh.core.service.DbServiceCacheDecorator;
import ru.otus.lantukh.hibernate.HibernateUtils;
import ru.otus.lantukh.hibernate.dao.UserDaoHibernate;
import ru.otus.lantukh.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class
        );

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        HwCache<Integer, User> cache = new MyCache<>();
        DBServiceUser dbServiceUser = new DbServiceCacheDecorator(new DbServiceUserImpl(userDao), cache);

        User user1 = new User(0, "Вася");
        user1.setAddress(new AddressDataSet(0, "г. Москва, ул Фестивальная, д. 52"));
        user1.addPhone(new PhoneDataSet(0, "+79998887766"));

        long id = dbServiceUser.saveUser(user1);

        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        User user2 = new User(1L, "А! Нет. Это же совсем не Вася");
        user2.setAddress(new AddressDataSet(1L, "г. Москва, ул Льва Толстого, д. 16"));
        user2.addPhone(new PhoneDataSet(1L, "+79853334422"));

        id = dbServiceUser.saveUser(user2);

        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);
        Optional<User> mayBeUpdatedUser2 = dbServiceUser.getUser(id);

        System.out.println(mayBeCreatedUser);
        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
