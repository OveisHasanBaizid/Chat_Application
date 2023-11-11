package com.example.chat.dataBase;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.MessagePrivate;
import com.example.chat.models.User;
import javafx.util.converter.DateStringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseMessagePrivate {
    Connection connection;
    User userCurrent;
    public DataBaseMessagePrivate() {
        this.connection = ConnectionDataBase.getConnection();
        userCurrent = HelperSendingObject.getUserCurrent();
    }

    public List<MessagePrivate> getConversation(String senderUserName, String receiverUserName) throws SQLException {
        List<MessagePrivate> messages = new ArrayList<>();
        String sql = """
                DECLARE @SenderUserName decimal(18,0)
                DECLARE @ReceiverUserName decimal(18,0)
                  
                SET @SenderUserName = ?
                SET @ReceiverUserName = ?
                               
                SELECT * FROM dbo.tblMessagesPrivate
                where (dbo.tblMessagesPrivate.SenderID = @SenderUserName AND dbo.tblMessagesPrivate.ReceiverUserID = @ReceiverUserName)
                OR (dbo.tblMessagesPrivate.SenderID = @ReceiverUserName AND dbo.tblMessagesPrivate.ReceiverUserID = @SenderUserName)     
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, senderUserName);
        statement.setString(2, receiverUserName);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            messages.add(covertToUser(result));
        }
        return messages;
    }
    public void sendMessage(String text , int receiverId) throws SQLException {
        String sql = """
                INSERT INTO [tblMessagesPrivate]
                           ([SenderID]
                           ,[MessageDateTime]
                           ,[MessageBudy]
                           ,[ReceiverUserID])
                     VALUES
                           (?
                           ,GETDATE()
                           ,?
                           ,?)
                GO
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(userCurrent.getId()));
        statement.setString(2, text);
        statement.setString(3, String.valueOf(receiverId));
        statement.executeQuery();
    }
    public MessagePrivate covertToUser(ResultSet result) throws SQLException {
        return new MessagePrivate(Integer.parseInt(result.getString(1))
                , Integer.parseInt(result.getString(2))
                , LocalDate.now()
                , result.getString(4)
                , Integer.parseInt(result.getString(5)));
    }
}
