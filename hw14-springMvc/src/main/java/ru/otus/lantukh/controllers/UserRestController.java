package ru.otus.lantukh.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.lantukh.model.AddressDataSet;
import ru.otus.lantukh.model.PhoneDataSet;
import ru.otus.lantukh.model.User;
import ru.otus.lantukh.service.DBServiceUser;

import java.util.Map;

@RestController
public class UserRestController {
    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private final DBServiceUser dbServiceUser;

    public UserRestController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @PostMapping("/api/user")
    public RedirectView saveUser(@RequestParam Map<String,String> allParams) {
        User user = new User(0, allParams.get("name"));
        user.setAddress(new AddressDataSet(0, allParams.get("address")));
        user.addPhone(new PhoneDataSet(0, allParams.get("phone")));

        dbServiceUser.saveUser(user);
        return new RedirectView("/user/list", true);
    }
}
