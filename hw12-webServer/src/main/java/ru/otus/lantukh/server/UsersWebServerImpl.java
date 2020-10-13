package ru.otus.lantukh.server;

import org.eclipse.jetty.servlet.FilterHolder;
import ru.otus.lantukh.helpers.FileSystemHelper;
import ru.otus.lantukh.core.service.DBServiceUser;
import ru.otus.lantukh.services.TemplateProcessor;
import ru.otus.lantukh.services.UserAuthService;
import ru.otus.lantukh.servlet.AuthorizationFilter;
import ru.otus.lantukh.servlet.LoginServlet;
import ru.otus.lantukh.servlet.UsersApiServlet;
import ru.otus.lantukh.servlet.UsersServlet;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;

public class UsersWebServerImpl implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final DBServiceUser dbServiceUser;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;
    private final UserAuthService authService;

    public UsersWebServerImpl(int port,
                              UserAuthService authService,
                              DBServiceUser dbServiceUser,
                              Gson gson,
                              TemplateProcessor templateProcessor) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        this.authService = authService;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*"));


        server.setHandler(handlers);
        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String...paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder usersServlet = new ServletHolder(new UsersServlet(templateProcessor, dbServiceUser));
        ServletHolder usersApiServlet = new ServletHolder(new UsersApiServlet(dbServiceUser, gson));
        usersApiServlet.getRegistration().setMultipartConfig(new MultipartConfigElement(""));

        servletContextHandler.addServlet(usersServlet, "/users");
        servletContextHandler.addServlet(usersApiServlet, "/api/user");
        return servletContextHandler;
    }
}
