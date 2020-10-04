package ru.otus.lantukh.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lantukh.cache.MyCache;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.cache.HwCache;

import java.util.Optional;

public class DbServiceCacheDecorator implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceCacheDecorator.class);
    DBServiceUser dbServiceUser;
    HwCache<Long, User> cache;

    public DbServiceCacheDecorator(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
        this.cache = new MyCache<>();
    };


    @Override
    public long saveUser(User user) {
        long id = user.getId();
        User userInCache = cache.get(id);

        if (!user.equals(userInCache)) {
            long userId = dbServiceUser.saveUser(user);
            user.setId(userId);
            cache.put(userId, user);
            logger.info("saved user to cache: {}", user);

            return userId;
        }
        logger.info("pulled user from cache: {}", user);

        return id;
    }

    @Override
    public Optional<User> getUser(long id) {
        User user = cache.get(id);

        if (user == null) {
            Optional<User> userFromDb = dbServiceUser.getUser(id);
            userFromDb.ifPresent(value -> cache.put(user.getId(), value));

            return userFromDb;
        }
        logger.info("pulled user from cache: {}", user);

        return Optional.of(user);
    }
}
