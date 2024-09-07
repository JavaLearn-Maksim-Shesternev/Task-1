package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Dmitriy", "Prohorov", (byte) 40);
        userService.saveUser("Svetlana", "Stepanova", (byte) 34);
        userService.saveUser("Alexander", "Sazonov", (byte) 23);
        userService.saveUser("Elena", "Ivanitskaya", (byte) 37);

        List<User> users = userService.getAllUsers();
        for (User i : users) {
            System.out.println(i);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
