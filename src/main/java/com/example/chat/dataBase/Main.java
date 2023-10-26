package com.example.chat.dataBase;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        System.out.println(dataBaseUser.findUserByPhone("+98-911-125-3620"));
    }
}
