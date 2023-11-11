package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseMessagePrivate;
import com.example.chat.models.MessagePrivate;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ChatController {
    @FXML
    Circle circle_image;
    @FXML
    Label lb_name;

    @FXML
    VBox vbox_message;

    @FXML
    ScrollPane scrollPane;
    User user, userCurrent;

    public void initialize() throws FileNotFoundException {
        user = (User) HelperSendingObject.getObject();
        userCurrent = HelperSendingObject.getUserCurrent();
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_1.jpeg"))));
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                lb_name.setText(user.getName());
                try {
                    setMessage();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    public void setMessage() throws SQLException {
        DataBaseMessagePrivate dataBaseMessagePrivate = new DataBaseMessagePrivate();
        vbox_message.getChildren().clear();
        vbox_message.setSpacing(10);
        for (MessagePrivate message : dataBaseMessagePrivate
                .getConversation("1", "2")) {
            HBox messageBox = new HBox();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("custom_message_pv.fxml"));
            try {
                HelperSendingObject.setObject(message);
                Parent root = loader.load();

                if (message.getSenderId() == userCurrent.getId())
                    messageBox.setStyle("-fx-alignment: center-right;");
                else
                    messageBox.setStyle("-fx-alignment: center-left;");

                messageBox.getChildren().add(root);
                vbox_message.getChildren().add(messageBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scrollPane.vvalueProperty().bind(vbox_message.heightProperty());
    }
}
