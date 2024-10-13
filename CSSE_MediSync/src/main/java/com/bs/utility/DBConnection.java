package com.bs.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url = "jdbc:mysql://localhost:3306/medisync?useSSL=false&serverTimezone=UTC";
    private static String userName = "root";
    private static String password = " ";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connectivity failed!");
            e.printStackTrace();
        }
        return con;
    }
}
