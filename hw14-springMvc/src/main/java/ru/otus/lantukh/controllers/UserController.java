package ru.otus.lantukh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.lantukh.model.User;
import ru.otus.lantukh.service.DBServiceUser;

import java.util.List;

@Controller
public class UserController {

    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping({"/user/list"})
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getUsers();
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }
}
