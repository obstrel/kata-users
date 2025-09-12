package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    final static String TABLE_NAME = "Users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        if(!Util.isTableExists(TABLE_NAME)) {
            final String sql =
                    "CREATE TABLE `"
                            + TABLE_NAME
                            + "` (`Id` BIGINT NOT NULL AUTO_INCREMENT , `Name` VARCHAR(100) NULL , `LastName` VARCHAR(100) NULL , `Age` INT NULL , PRIMARY KEY (`Id`)) ENGINE = InnoDB;";
            Util.execQuery(sql, null);
        }

    }

    public void dropUsersTable() {
        if(Util.isTableExists(TABLE_NAME)) {
            final String sql =
                    "DROP TABLE `" + TABLE_NAME + "`";
            Util.execQuery(sql, null);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        final String sql =
                "INSERT INTO " + TABLE_NAME
                        + " (name, lastName, age) VALUES (?, ?, ?)";
        Util.execQuery(sql, preparedStatement -> {
            try {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        final String sql =
                "SELECT * FROM " + TABLE_NAME;

        List<User> users = new ArrayList<>();

        Util.getData(sql, resultSet -> {
            try {
                while (resultSet.next()) {
                    User user = new User(resultSet.getString("Name"), resultSet.getString("LastName"), resultSet.getByte("Age"));

                    users.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return users;
    }

    public void cleanUsersTable() {
        final String sql =
                "DELETE FROM " + TABLE_NAME;
        Util.execQuery(sql, null);

    }
}
