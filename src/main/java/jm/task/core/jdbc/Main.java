package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        User[] testUsers = {
                new User("name0", "lastName0", (byte) 0),
                new User("name1", "lastName1", (byte) 1),
                new User("name2", "lastName2", (byte) 2),
                new User("name3", "lastName3", (byte) 3)
        };

        UserServiceImpl us = new UserServiceImpl();

        us.createUsersTable();

        Arrays.stream(testUsers).forEach(user -> {addUser(us, user);});

        printAllUsers(us);
        us.removeUserById(1);
        clearUsers(us);
        us.dropUsersTable();

    }

    private static void clearUsers(UserServiceImpl us) {
        us.cleanUsersTable();
    }

    private static void printAllUsers(UserServiceImpl us) {
        us.getAllUsers().forEach(System.out::println);
    }

    public static void addUser(UserServiceImpl userService, User user) {
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.printf("User с именем — %s добавлен в базу данных", user.getName());
    }
}
