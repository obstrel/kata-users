package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao getUserDao() {
        return new UserDaoHibernateImpl();
    }

    public void createUsersTable() {
        UserDao userDao = getUserDao();
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        UserDao userDao = getUserDao();
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        UserDao userDao = getUserDao();
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        UserDao userDao = getUserDao();
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        UserDao userDao = getUserDao();

        userDao.cleanUsersTable();
    }
}
