package org.sulaiman.dbConnection;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.Scanner;

public class MySQLConnection {
//    private final static Dotenv dotenv = Dotenv.load();
    // TODO: Dotenv should be used to load the environment variables from the .env file after building the project from the same directory
    private static Dotenv dotenv = null;
    static {
        checkEnvFile();
        try {
            dotenv = Dotenv.configure()
                    .directory("./")
                    .load();
        }catch (Exception e) {
            System.out.println("Error loading .env file: " + e.getMessage());
        }
    }
    private final static String username = dotenv.get("DB_USER");
    private final static String password = dotenv.get("DB_PASS");
    private final static String host = dotenv.get("DB_HOST");
    private final static String database = dotenv.get("DB_NAME");
    private final static int port = Integer.parseInt(dotenv.get("DB_PORT"));

    public static Connection getConnection() {
        try {
            String mysqlUrl = "jdbc:mysql://" + host + ":" + port + "/" + database +
                    "?useSSL=false&serverTimezone=UTC";
            return DriverManager.getConnection(mysqlUrl, username, password);
        }catch (SQLException e){
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

    public static String getDatabaseName() {
        return database;
    }

    public static void checkEnvFile() {
        File envFile = new File(".env");

        if (!envFile.exists()) {
            System.out.println("Error: .env file does not exist. Creating one now...");

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Please enter the MySQL database host: ");
                String host = scanner.nextLine();
                System.out.print("Please enter the MySQL database port (default is 3306): ");
                String port = scanner.nextLine();
                System.out.print("Please enter the MySQL database name: ");
                String database = scanner.nextLine();
                System.out.print("Please enter the MySQL database username: ");
                String username = scanner.nextLine();
                System.out.print("Please enter the MySQL database password: ");
                String password = scanner.nextLine();

                // Create the file and write the contents
                if (envFile.createNewFile()) {
                    try (FileWriter writer = new FileWriter(envFile)) {
                        writer.write("DB_HOST=" + host + "\n");
                        writer.write("DB_PORT=" + (port.isEmpty() ? "3306" : port) + "\n");
                        writer.write("DB_NAME=" + database + "\n");
                        writer.write("DB_USER=" + username + "\n");
                        writer.write("DB_PASS=" + password + "\n");
                    }
                    System.out.println("Created .env file successfully.");
                    System.out.println("Please restart the application.");
                    System.exit(0);
                } else {
                    System.out.println("Error: Failed to create .env file.");
                }
            } catch (Exception e) {
                System.out.println("Error creating .env file: " + e.getMessage());
            }
        }
    }
}
