package ru.otus.lantukh.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.cache.HwCache;

import java.util.Optional;

public class DbServiceCacheDecorator implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceCacheDecorator.class);
    private final DBServiceUser dbServiceUser;
    private final HwCache<Integer, User> cache;

    public DbServiceCacheDecorator(DBServiceUser dbServiceUser, HwCache<Integer, User> cache) {
        this.dbServiceUser = dbServiceUser;
        this.cache = cache;
    };


    @Override
    public long saveUser(User user) {
        long userId = dbServiceUser.saveUser(user);
        user.setId(userId);
        cache.put((int) userId, user);
        logger.info("saved user to cache: {}", user);

        return userId;
    }

    @Override
    public Optional<User> getUser(long id) {
        User user = cache.get((int) id);

        if (user == null) {
            Optional<User> userFromDb = dbServiceUser.getUser(id);
            userFromDb.ifPresent(value -> cache.put((int) id, value));

            return userFromDb;
        }
        logger.info("pulled user from cache: {}", user);

        return Optional.of(user);
    }
}
