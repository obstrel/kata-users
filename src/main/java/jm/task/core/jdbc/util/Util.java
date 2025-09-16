package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.function.Consumer;

public class Util {

    public static void consumeConnection(Consumer<Connection> connectionConsumer) {
        // Параметры подключения
        String url = "jdbc:mysql://localhost:3306/kata-jdbc";
        String user = "root";
        String password = "admin";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                connectionConsumer.accept(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isTableExists(String tableName) {
        final boolean[] ret = {false};

        consumeConnection(connection -> {
            try {
                DatabaseMetaData metaData = connection.getMetaData();

                ret[0] = metaData.getTables(null, null, tableName, null).next();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return ret[0];
    }

    // реализуйте настройку соеденения с БД
}
