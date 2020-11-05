package ru.otus.lantukh.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.lantukh.model.AddressDataSet;
import ru.otus.lantukh.model.PhoneDataSet;
import ru.otus.lantukh.model.User;
import ru.otus.lantukh.dto.UserDto;
import ru.otus.lantukh.service.DBServiceUser;

@RestController
public class UserRestController {
    private final DBServiceUser dbServiceUser;

    public UserRestController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @PostMapping("/api/user")
    public long saveUser(@ModelAttribute UserDto userDto) {
        User user = new User(0, userDto.getName());
        user.setAddress(new AddressDataSet(0, userDto.getAddress()));
        user.addPhone(new PhoneDataSet(0, userDto.getPhone()));

        return dbServiceUser.saveUser(user);
    }
}
