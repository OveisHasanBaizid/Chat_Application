package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.MessagePrivate;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MessagePrivateController {
    @FXML
    Text txt_message, txt_date;
    @FXML
    Pane pane;

    MessagePrivate messagePrivate;

    User userCurrent;

    public void initialize() {
        messagePrivate = (MessagePrivate) HelperSendingObject.getObject();
        userCurrent = HelperSendingObject.getUserCurrent();

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (messagePrivate != null) {
                txt_message.setText(messagePrivate.getBody());
                txt_date.setText(messagePrivate.getSendingDateTime().toString());
                String style = "-fx-border-color: #000000; -fx-padding: 5; -fx-border-radius: 5";
                if (messagePrivate.getSenderId() == userCurrent.getId()) {
                    pane.setStyle("-fx-background-color: #01eca3; " + style);
                } else {
                    pane.setStyle("-fx-background-color: #9a63ed; " + style);
                }
            }
        }).start();
    }
}
