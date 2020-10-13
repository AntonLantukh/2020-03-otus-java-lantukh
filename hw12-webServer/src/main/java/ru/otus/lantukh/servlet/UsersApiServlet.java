package ru.otus.lantukh.servlet;

import com.google.gson.reflect.TypeToken;
import ru.otus.lantukh.core.model.AddressDataSet;
import ru.otus.lantukh.core.model.PhoneDataSet;
import ru.otus.lantukh.core.service.DBServiceUser;
import ru.otus.lantukh.core.model.User;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UsersApiServlet extends HttpServlet {
    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = dbServiceUser.getUsers();
        Type listType = new TypeToken<List<User>>() {}.getType();
        System.out.println(users);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String json = gson.toJson(users.toArray());
        out.print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        User user = new User(0, name);
        user.setAddress(new AddressDataSet(0, address));
        user.addPhone(new PhoneDataSet(0, phone));

        dbServiceUser.saveUser(user);
    }
}
