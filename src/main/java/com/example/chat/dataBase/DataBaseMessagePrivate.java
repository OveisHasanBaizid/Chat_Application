package com.example.chat.dataBase;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.MessagePrivate;
import com.example.chat.models.User;

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

    public List<MessagePrivate> getConversation(int senderUserName, int receiverUserName) throws SQLException {
        List<MessagePrivate> messages = new ArrayList<>();
        String sql = """
                DECLARE @SenderID decimal(18,0)
                DECLARE @ReceiverID decimal(18,0)
                  
                SET @SenderID = ?
                SET @ReceiverID = ?
                               
                SELECT * FROM dbo.tblMessagesPrivate
                where (dbo.tblMessagesPrivate.SenderID = @SenderID AND dbo.tblMessagesPrivate.ReceiverUserID = @ReceiverID)
                OR (dbo.tblMessagesPrivate.SenderID = @ReceiverID AND dbo.tblMessagesPrivate.ReceiverUserID = @SenderID)     
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(senderUserName));
        statement.setString(2, String.valueOf(receiverUserName));
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            messages.add(covertToMessagePrivate(result));
        }
        return messages;
    }
    public void sendMessage(String text , int receiverId) throws SQLException {
        String sql = """
                INSERT INTO [tblMessagesPrivate] ([SenderID] , [MessageDateTime] , [MessageBudy] , [ReceiverUserID])
                     VALUES(? , GETDATE() , ? , ?)
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(userCurrent.getId()));
        statement.setString(2, text);
        statement.setString(3, String.valueOf(receiverId));
        statement.executeUpdate();
    }
    public MessagePrivate covertToMessagePrivate(ResultSet result) throws SQLException {
        return new MessagePrivate(Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2))
                , LocalDate.now(), result.getString(4), Integer.parseInt(result.getString(5)));
    }
}
