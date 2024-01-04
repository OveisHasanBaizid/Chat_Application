package com.example.chat.dataBase;

import com.example.chat.models.Group;
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
        statement.setString(1, phone);
        ResultSet result = statement.executeQuery();
        if (result.next())
            return covertToUser(result);
        return null;
    }

    public boolean isExistUserName(String phone) throws SQLException {
        String sql = "SELECT * FROM tblUsers where IUserName=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, phone);
        ResultSet result = statement.executeQuery();
        return result.next();
    }

    public User findUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM tblUsers where IUserID=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        if (result.next())
            return covertToUser(result);
        return null;
    }

    public User covertToUser(ResultSet result) throws SQLException {
        return new User(result.getInt(1), result.getInt(2), result.getString(3), result.getString(4), result.getInt(5) != 0);
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

    public List<User> getAllContact(int userId) throws SQLException {
        List<User> contacts = new ArrayList<>();
        String sql = """
                Select IUserID , IUserCountryID , IUserName , IUserPhone , IsIUserBlocked 
                FROM dbo.tblUsers AS Usr INNER JOIN tblContactLists as list 
                on usr.IUserID = list.UserInContactListID where list.MainUserID = ?
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            contacts.add(covertToUser(result));
        }
        return contacts;
    }

    public List<Group> getAlGroups(int userId) throws SQLException {
        List<Group> groups = new ArrayList<>();
        String sql = "select GroupID from tblGroupMembers where UserID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(userId));
        ResultSet result = statement.executeQuery();
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        while (result.next()) {
            groups.add(dataBaseGroup.findGroupById(result.getInt(1)));
        }
        return groups;
    }

    public void insertUser(User user, String prefixPhone) throws SQLException {
        String sql = """
                INSERT INTO dbo.tblUsers (IUserCountryID, IUserName, IUserPhone, IsIUserBlocked)
                SELECT CountryID, ? , ?, 0 FROM dbo.tblCountry WHERE PreNumber = ?
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPhone());
        statement.setString(3, prefixPhone);
        statement.executeUpdate();
    }

    public void addContact(int currentUserId, int contractId) throws SQLException {
        String sql = """
                INSERT INTO dbo.tblContactLists (MainUserID, UserInContactListID)
                VALUES (? ,?)
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, currentUserId);
        statement.setInt(2, contractId);
        statement.executeUpdate();
    }
}
