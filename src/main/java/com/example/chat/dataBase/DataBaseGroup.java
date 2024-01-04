package com.example.chat.dataBase;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.Group;
import com.example.chat.models.GroupMember;
import com.example.chat.models.MessageGroup;
import com.example.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseGroup {
    Connection connection;
    User userCurrent;

    public DataBaseGroup() {
        this.connection = ConnectionDataBase.getConnection();
        userCurrent = HelperSendingObject.getUserCurrent();

    }

    public Group covertToGroup(ResultSet result) throws SQLException {
        return new Group(result.getInt(1), result.getString(2), result.getInt(3), null);
    }

    public GroupMember covertToGroupMember(ResultSet result) throws SQLException {
        return new GroupMember(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4) == 1);
    }

    public Group findGroupById(int idGroup) throws SQLException {
        String sql = "select * from tblGroups where GroupID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idGroup);
        ResultSet result = statement.executeQuery();
        if (result.next())
            return covertToGroup(result);
        return null;
    }

    public List<MessageGroup> getConversation(int groupId) throws SQLException {
        List<MessageGroup> messages = new ArrayList<>();
        String sql = "select * from tblMessageToGroups where GroupID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, groupId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            messages.add(covertToMessageGroup(result));
        }
        return messages;
    }

    public MessageGroup covertToMessageGroup(ResultSet result) throws SQLException {
        return new MessageGroup(result.getInt(1), result.getInt(2), LocalDate.now(), result.getString(4), result.getInt(5));
    }

    public List<GroupMember> getAllGroupMembers(int groupId) throws SQLException {
        List<GroupMember> groupMembers = new ArrayList<>();
        String sql = "SELECT * FROM tblGroupMembers where GroupID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, groupId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            groupMembers.add(covertToGroupMember(result));
        }
        return groupMembers;
    }

    public void sendMessage(String messageBody, int groupId) throws SQLException {
        String sql = """
                INSERT INTO [tblMessageToGroups]([SenderID], [MessageDateTime], [MessageBudy], [GroupID])
                     VALUES(?, GETDATE(), ?, ?)
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userCurrent.getId());
        statement.setString(2, messageBody);
        statement.setInt(3, groupId);
        statement.executeUpdate();
    }

    public void removeUserOfGroup(int groupId, int userId) throws SQLException {
        String sql = """
                DELETE FROM tblGroupMembers 
                WHERE  GroupID = ? and UserID = ?
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, groupId);
        statement.setInt(2, userId);
        statement.executeUpdate();
    }

    public void addUserToGroup(int groupId, int userId) throws SQLException {
        String sql = """
                INSERT INTO [tblGroupMembers]([GroupID], [UserID], [IsThatMemberAdmin])
                     VALUES(?, ?, 0)
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, groupId);
        statement.setInt(2, userId);
        statement.executeUpdate();
    }
    public int countMembers(int groupId) throws SQLException {
        String sql = """
                select count(*) from dbo.tblGroupMembers where dbo.tblGroupMembers.GroupID = ?
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, groupId);
        ResultSet result = statement.executeQuery();
        if (result.next())
            return result.getInt(1);
        return 0;
    }
    public List<User> filterUserAreNotInGroup(int groupId) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = """
                SELECT Users.IUserID, Users.IUserCountryID, Users.IUserName, Users.IUserPhone, Users.IsIUserBlocked
                FROM dbo.tblUsers AS Users
                INNER JOIN dbo.tblContactLists AS ContactLists ON Users.IUserID = ContactLists.UserInContactListID and ContactLists.MainUserID = ?
                LEFT JOIN dbo.tblGroupMembers AS GroupMembers ON Users.IUserID = GroupMembers.UserID and GroupMembers.GroupID = ?
                where GroupMembers.GroupID IS NULL
                                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userCurrent.getId());
        statement.setInt(2, groupId);
        ResultSet result = statement.executeQuery();
        DataBaseUser dataBaseUser = new DataBaseUser();
        while (result.next()) {
            users.add(dataBaseUser.covertToUser(result));
        }
        return users;
    }

    public void addNewGroup(String nameGroup) throws SQLException {
        String sql = """
                INSERT INTO [tblGroups]([GroupName], [GroupCreatorID], [GroupCreationDate])
                     VALUES(?, ?, GETDATE())
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nameGroup);
        statement.setInt(2, userCurrent.getId());
        statement.executeUpdate();
    }
}
