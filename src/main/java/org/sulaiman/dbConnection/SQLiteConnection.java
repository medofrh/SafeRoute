package org.sulaiman.dbConnection;

import java.sql.*;
import java.io.File;
public class SQLiteConnection {
    // set database in resources in database folder
    private static final String sqliteUrl = "jdbc:sqlite:local.db";
    public static Connection getConnection() {
        try {
            // save sqlite database in resources in database folder
            return DriverManager.getConnection(sqliteUrl);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null;
        } catch (SQLException e) {
            return false;
        }
    }

    public static String getSqliteUrl() {
        return sqliteUrl;
    }

    // remove the database file
    public static void removeDatabase() {
        try {
            // Extract the file path from the URL
            String filePath = sqliteUrl.replace("jdbc:sqlite:", "");

            // Create a new file object
            File file = new File(filePath);

            // Check if the file exists
            if (file.exists()) {
                // Delete the file
                file.delete();
            }
        }catch (Exception e) {
           return;
        }
    }

    // check if the database file exists
    public static boolean databaseExists() {
        try {
            // Extract the file path from the URL
            String filePath = sqliteUrl.replace("jdbc:sqlite:", "");

            // Create a new file object
            File file = new File(filePath);

            // Check if the file exists
            return file.exists();
        }catch (Exception e) {
            return false;
        }
    }
}
