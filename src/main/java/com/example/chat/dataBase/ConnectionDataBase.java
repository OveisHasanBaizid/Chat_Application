package com.example.chat.dataBase;

import java.sql.*;

public class ConnectionDataBase {
    private static Connection connection;
    static {
        String connectionString = "jdbc:sqlserver://DESKTOP-1TDK8JA;database=InstantMessagingDB;"+
                "encrypt=false;trustServerCertificate=true;integratedSecurity=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection  = DriverManager.getConnection(connectionString);
                System.out.println("Connect is successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("The connection to the database failed.");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
