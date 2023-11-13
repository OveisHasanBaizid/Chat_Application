package com.example.chat.dataBase;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.*;

import java.sql.*;
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
        return new Group(result.getInt(1)
                , result.getString(2)
                , result.getInt(3)
                , null);
    }
    public GroupMember covertToGroupMember(ResultSet result) throws SQLException {
        return new GroupMember(result.getInt(1)
                , result.getInt(2)
                , result.getInt(3)
                , result.getInt(4) == 1);
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
        return new MessageGroup(result.getInt(1)
                , result.getInt(2)
                , LocalDate.now()
                , result.getString(4)
                , result.getInt(5));
    }
    public List<GroupMember> getAllGroupMembers(int groupId) throws SQLException {
        List<GroupMember> groupMembers = new ArrayList<>();
        String sql = "SELECT * FROM tblGroupMembers where GroupID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,groupId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            groupMembers.add(covertToGroupMember(result));
        }
        return groupMembers;
    }
    public void sendMessage(String messageBody , int groupId) throws SQLException {
        String sql = """
                INSERT INTO [tblMessageToGroups]
                           ([SenderID]
                           ,[MessageDateTime]
                           ,[MessageBudy]
                           ,[GroupID])
                     VALUES
                           (?
                           ,GETDATE()
                           ,?
                           ,?)
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,userCurrent.getId());
        statement.setString(2, messageBody);
        statement.setInt(3, groupId);
        statement.executeUpdate();
    }
}
