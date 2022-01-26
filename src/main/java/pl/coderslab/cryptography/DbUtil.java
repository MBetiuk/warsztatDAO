package pl.coderslab.cryptography;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {


    public static final String QUERY = "";

    public static final String URL;

    static {
        String databaseName = "workshop2";
        URL = "jdbc:mysql://localhost/" + databaseName;
    }

    public static final String USER = "root";

    public static final String PASSWORD = "coderslab";


    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        return conn;
    }
}
