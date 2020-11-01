package ru.otus.lantukh;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import ru.otus.lantukh.dao.UserDao;
import ru.otus.lantukh.model.AddressDataSet;
import ru.otus.lantukh.model.PhoneDataSet;
import ru.otus.lantukh.model.User;
import ru.otus.lantukh.service.DBServiceUser;
import ru.otus.lantukh.service.DbServiceUserImpl;
import ru.otus.lantukh.hibernate.HibernateUtils;
import ru.otus.lantukh.dao.UserDaoHibernate;
import ru.otus.lantukh.sessionmanager.SessionManagerHibernate;

@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("noHandlerView");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
    }

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
