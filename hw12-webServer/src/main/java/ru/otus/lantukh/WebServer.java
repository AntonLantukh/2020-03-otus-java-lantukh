package ru.otus.lantukh;

import ru.otus.lantukh.core.dao.UserDao;
import ru.otus.lantukh.core.model.AddressDataSet;
import ru.otus.lantukh.core.model.PhoneDataSet;
import ru.otus.lantukh.core.model.User;
import ru.otus.lantukh.core.service.DBServiceUser;
import ru.otus.lantukh.core.service.DbServiceUserImpl;
import ru.otus.lantukh.hibernate.HibernateUtils;
import ru.otus.lantukh.hibernate.dao.UserDaoHibernate;
import ru.otus.lantukh.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.lantukh.server.UsersWebServer;
import ru.otus.lantukh.server.UsersWebServerImpl;
import ru.otus.lantukh.services.TemplateProcessor;
import ru.otus.lantukh.services.TemplateProcessorImpl;
import ru.otus.lantukh.services.UserAuthService;
import ru.otus.lantukh.services.UserAuthServiceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServer {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class
        );

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UserAuthService authService = new UserAuthServiceImpl();

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT, authService, dbServiceUser, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
