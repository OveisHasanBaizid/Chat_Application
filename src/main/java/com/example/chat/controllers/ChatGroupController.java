package com.example.chat.controllers;

import com.example.chat.Main;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseGroup;
import com.example.chat.models.Group;
import com.example.chat.models.MessageGroup;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ChatGroupController {
    @FXML
    Circle circle_image;
    @FXML
    Label lb_name;

    @FXML
    VBox vbox_message;

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextField td_message;

    User userCurrent;

    Group group;

    public void initialize() throws FileNotFoundException {
        group = HelperSendingObject.getGroup();
        userCurrent = HelperSendingObject.getUserCurrent();
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("images\\group_icon.jpeg"))));
        initialize2();
        actionCircleImage();
    }

    public void actionCircleImage() {
        circle_image.setOnMouseClicked(mouseEvent -> {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("group_details_page.fxml"));
            try {
                HelperSendingObject.setGroup(group);
                Pane pane = HelperSendingObject.getPaneChat();
                pane.getChildren().clear();
                pane.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void initialize2() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                lb_name.setText(group.getName());
                try {
                    setMessage();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    public void setMessage() throws SQLException {
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        vbox_message.getChildren().clear();
        vbox_message.setSpacing(10);
        for (MessageGroup message : dataBaseGroup
                .getConversation(group.getId())) {
            HBox messageBox = new HBox();
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("custom_message_group.fxml"));
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

    public void btnSend() throws SQLException {
        String message = td_message.getText();
        if (message.isEmpty())
            return;
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        dataBaseGroup.sendMessage(message, group.getId());
        setMessage();
        td_message.clear();
    }
}
