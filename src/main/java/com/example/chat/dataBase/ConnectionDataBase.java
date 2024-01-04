package com.example.chat.dataBase;

import java.sql.*;

public class ConnectionDataBase {
    private static Connection connection = null;
    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jdbc:sqlserver://DESKTOP-1TDK8JA\\Oveis;")
                .append("database=InstantMessagingDB;")
                .append("encrypt=false;")
                .append("trustServerCertificate=true;")
                .append("username=admin;")
                .append("password=admin12345;");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection  = DriverManager.getConnection(stringBuilder.toString());
                System.out.println("Connect is successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("The connection to the database failed.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
