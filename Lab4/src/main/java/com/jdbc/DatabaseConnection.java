package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {
    private static DatabaseConnection instance;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/mysql";
    private final String username = "root";
    private final String password = "root";

    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
