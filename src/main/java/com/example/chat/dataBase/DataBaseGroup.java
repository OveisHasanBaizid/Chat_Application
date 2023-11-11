package com.example.chat.dataBase;

import com.example.chat.models.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseGroup {
    Connection connection;

    public DataBaseGroup() {
        this.connection = ConnectionDataBase.getConnection();
    }

    public Group covertToGroup(ResultSet result) throws SQLException {
        return new Group(result.getInt(1)
                , result.getString(2)
                , result.getInt(3)
                , null);
    }

    public Group findGroupById(int idGroup) throws SQLException {
        String sql = "select * from tblGroups where GroupID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(idGroup));
        ResultSet result = statement.executeQuery();
        if (result.next())
            return covertToGroup(result);
        return null;
    }
}
