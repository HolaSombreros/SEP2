package server.database;

import java.sql.*;

public class DatabaseManager {
    private String url;
    private String username;
    private String password;
    public static String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=sep2";
    public static String USERNAME = "sep2admin";
    public static String PASSWORD = "admin";
    private static DatabaseManager instance;
    private static Object lock = new Object();
    
    private DatabaseManager(String url, String username, String password) {
        this.password = password;
        this.url = url;
        this.username = username;
    }
    
    private DatabaseManager() {
        this(URL, USERNAME, PASSWORD);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null)
                    instance = new DatabaseManager();
            }
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}