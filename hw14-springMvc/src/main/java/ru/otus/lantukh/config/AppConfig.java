package ru.otus.lantukh.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.lantukh.dao.UserDao;
import ru.otus.lantukh.dao.UserDaoHibernate;
import ru.otus.lantukh.hibernate.HibernateUtils;
import ru.otus.lantukh.model.AddressDataSet;
import ru.otus.lantukh.model.PhoneDataSet;
import ru.otus.lantukh.model.User;
import ru.otus.lantukh.service.DBServiceUser;
import ru.otus.lantukh.service.DbServiceUserImpl;
import ru.otus.lantukh.sessionmanager.SessionManagerHibernate;

@Configuration
public class AppConfig {
    @Bean
    public SessionFactory getSessionFactory() {
        return HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class
        );
    }

    @Bean
    public SessionManagerHibernate getSessionManager(SessionFactory sessionFactory) {
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public UserDao getUserDao(SessionManagerHibernate sessionManager) {
        return new UserDaoHibernate(sessionManager);
    }

    @Bean
    public DBServiceUser getDbServiceUser(UserDao userDao) {
        return new DbServiceUserImpl(userDao);
    }
}
