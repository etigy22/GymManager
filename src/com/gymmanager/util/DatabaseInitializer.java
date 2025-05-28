package com.gymmanager.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initialize() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Initializing database tables...");

            String createMembers = """
                CREATE TABLE IF NOT EXISTS members (
                    member_id INTEGER PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    membership_type TEXT NOT NULL
                );
                """;

            String createClasses = """
                CREATE TABLE IF NOT EXISTS gym_classes (
                    class_id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    instructor_first_name TEXT NOT NULL,
                    instructor_last_name TEXT NOT NULL,
                    max_capacity INTEGER NOT NULL
                );
                """;

            String createEnrollments = """
                CREATE TABLE IF NOT EXISTS enrollments (
                    member_id INTEGER,
                    class_id INTEGER,
                    FOREIGN KEY (member_id) REFERENCES members(member_id),
                    FOREIGN KEY (class_id) REFERENCES gym_classes(class_id),
                    PRIMARY KEY (member_id, class_id)
                );
                """;

            stmt.execute(createMembers);
            System.out.println("Members table created successfully");

            stmt.execute(createClasses);
            System.out.println("Classes table created successfully");

            stmt.execute(createEnrollments);
            System.out.println("Enrollments table created successfully");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error initializing database", e);
            System.exit(1);
        }
    }
}