package ru.otus.lantukh.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.lantukh.dao.UserDao;
import ru.otus.lantukh.dao.UserDaoHibernate;
import ru.otus.lantukh.db.handlers.SaveUserDataRequestHandler;
import ru.otus.lantukh.front.FrontendService;
import ru.otus.lantukh.front.FrontendServiceImpl;
import ru.otus.lantukh.front.handlers.SaveUserDataResponseHandler;
import ru.otus.lantukh.hibernate.HibernateUtils;
import ru.otus.lantukh.messagesystem.HandlersStore;
import ru.otus.lantukh.messagesystem.HandlersStoreImpl;
import ru.otus.lantukh.messagesystem.MessageSystem;
import ru.otus.lantukh.messagesystem.MessageSystemImpl;
import ru.otus.lantukh.messagesystem.client.CallbackRegistry;
import ru.otus.lantukh.messagesystem.client.CallbackRegistryImpl;
import ru.otus.lantukh.messagesystem.client.MsClient;
import ru.otus.lantukh.messagesystem.client.MsClientImpl;
import ru.otus.lantukh.messagesystem.message.MessageType;
import ru.otus.lantukh.model.AddressDataSet;
import ru.otus.lantukh.model.PhoneDataSet;
import ru.otus.lantukh.model.User;
import ru.otus.lantukh.db.DBServiceUser;
import ru.otus.lantukh.db.DbServiceUserImpl;
import ru.otus.lantukh.sessionmanager.SessionManagerHibernate;

@Configuration
public class AppConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

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

    @Bean
    public FrontendService getFrontendService(DBServiceUser dbServiceUser) {
        MessageSystem messageSystem = new MessageSystemImpl();
        CallbackRegistry callbackRegistry = new CallbackRegistryImpl();

        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();

        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA_SAVE, new SaveUserDataRequestHandler(dbServiceUser));
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA_SAVE, new SaveUserDataResponseHandler(callbackRegistry));

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);

        messageSystem.addClient(databaseMsClient);
        messageSystem.addClient(frontendMsClient);

        return frontendService;
    }
}
