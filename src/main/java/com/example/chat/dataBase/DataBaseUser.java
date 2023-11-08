package com.example.chat.dataBase;

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
    public  List<User> getAllContact(String name) throws SQLException {
        List<User> contacts = new ArrayList<>();
        String sql = """
                DECLARE @IUserName NVARCHAR(50)
                SET @IUserName = ?          
                SELECT     TOP (100) PERCENT tblMembers.IUserName AS MemnrUserName, tblMembers.IUserPhone AS MemberPhone
                FROM         dbo.tblUsers AS Usr INNER JOIN dbo.tblGroups AS Gp ON Usr.IUserID = Gp.GroupCreatorID INNER JOIN
                             dbo.tblGroupMembers AS Mgp ON Gp.GroupID = Mgp.GroupID INNER JOIN
                             dbo.tblUsers AS tblMembers ON Mgp.UserID = tblMembers.IUserID
                WHERE     (Usr.IUserName = @IUserName)
                GROUP BY tblMembers.IUserName, tblMembers.IUserPhone
                HAVING      (tblMembers.IUserName <> @IUserName)
                ORDER BY MemnrUserName
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            contacts.add(findUserByPhone(result.getString(2)));
        }
        return contacts;
    }
    public  List<String> getAlGroups(String name) throws SQLException {
        List<String> groups = new ArrayList<>();
        String sql = """
                DECLARE @IUserName NVARCHAR(50)
                SET @IUserName = ?             
                SELECT Gp.GroupName FROM  dbo.tblUsers AS Usr INNER JOIN
                                          dbo.tblGroups AS Gp ON Usr.IUserID = Gp.GroupCreatorID
                GROUP BY Gp.GroupName, Usr.IUserName
                HAVING      (Usr.IUserName = @IUserName)                  
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            groups.add(result.getString(1));
        }
        return groups;
    }


}
