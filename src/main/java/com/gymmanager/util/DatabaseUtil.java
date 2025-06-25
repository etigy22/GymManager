package com.gymmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:" +
            Paths.get(System.getProperty("user.dir"), "gym.db");
    private static final boolean driverLoaded;
    private static final List<Connection> activeConnections = new ArrayList<>();

    static {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            driverLoaded = true;
            System.out.println("SQLite JDBC driver loaded successfully");
            System.out.println("Database URL: " + DB_URL);
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC driver: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!driverLoaded) {
            throw new SQLException("SQLite JDBC driver not loaded");
        }
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            synchronized (activeConnections) {
                activeConnections.add(conn);
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }

    public static void closeAllConnections() {
        synchronized (activeConnections) {
            for (Connection conn : activeConnections) {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
            activeConnections.clear();
//            System.out.println("All database connections closed");
              System.out.println();
        }
    }
}