package com.example.chat.dataBase;

import com.example.chat.common.ConverterPhoneNumbers;
import com.example.chat.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUser {
    Connection connection;

    public DataBaseUser() {
        this.connection = ConnectionDataBase.getConnection();
    }

    public User findUserByPhone(String phone) throws SQLException {
        String sql = "SELECT * FROM tblUsers where IUserPhone=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,phone);
        ResultSet result = statement.executeQuery();
        if(result.next())
            return covertToUser(result);
        return null;
    }

    public User covertToUser(ResultSet result) throws SQLException {
        return new User(Integer.parseInt(result.getString(1))
                , Integer.parseInt(result.getString(2))
                , result.getString(3)
                , result.getString(4)
                , Integer.parseInt(result.getString(5)) != 0);
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM tblUsers";
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            users.add(covertToUser(result));
        }
        return users;
    }
}
