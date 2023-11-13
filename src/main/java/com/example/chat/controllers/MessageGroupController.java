package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.MessageGroup;
import com.example.chat.models.MessagePrivate;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class MessageGroupController {
    @FXML
    Text txt_message, txt_date, txt_user;
    @FXML
    Pane pane;

    MessageGroup messageGroup;

    User userCurrent;

    public void initialize() {
        messageGroup = (MessageGroup) HelperSendingObject.getObject();
        userCurrent = HelperSendingObject.getUserCurrent();
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (messageGroup != null) {
                txt_message.setText(messageGroup.getBody());
                txt_date.setText(messageGroup.getSendingDateTime().toString());
                String style = "-fx-border-color: #000000; -fx-padding: 5; -fx-border-radius: 5";
                if (messageGroup.getSenderId() == userCurrent.getId()) {
                    pane.setStyle("-fx-background-color: #01eca3; " + style);
                    txt_user.setText("You");
                } else {
                    pane.setStyle("-fx-background-color: #7ad6ef; " + style);
                    DataBaseUser dataBaseUser = new DataBaseUser();
                    try {
                        txt_user.setText(dataBaseUser.findUserById(messageGroup.getSenderId()).getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}
