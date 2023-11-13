package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.Group;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController {
    @FXML
    Pane chat_pane;

    @FXML
    VBox vbox_pv, vbox_group;

    @FXML
    TextField tf_name;

    User userCurrent;

    @FXML
    Circle circle_image;

    @FXML
    TabPane tabPane;

    public void initialize() throws FileNotFoundException {
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_1.jpeg"))));

        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            userCurrent = HelperSendingObject.getUserCurrent();
            tf_name.setText(userCurrent.getName());
            Platform.runLater(() -> {
                for (int i = 0; i < 2; i++) {
                    try {
                        addToListPv();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }).start();

        AtomicBoolean initGroupList = new AtomicBoolean(false);
        tabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if (!initGroupList.get() && newValue.intValue() == 1) {
                initGroupList.set(true);
                try {
                    addToListGroup();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void showChatPvPane() throws IOException {
        HelperSendingObject.setPaneChat(chat_pane);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat_pv_page.fxml"));
        chat_pane.getChildren().add(fxmlLoader.load());
    }
    public void showChatGroupPane() throws IOException {
        HelperSendingObject.setPaneChat(chat_pane);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat_group_page.fxml"));
        chat_pane.getChildren().clear();
        chat_pane.getChildren().add(fxmlLoader.load());
    }
    public void addToListPv() throws SQLException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        vbox_pv.getChildren().clear();
        for (User user : dataBaseUser.getAllContact(userCurrent.getName())) {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("custom_item_pv.fxml"));
            try {
                new Thread(() -> HelperSendingObject.setObject(user)).start();
                Parent root = loader.load();
                root.setOnMouseClicked(event -> {
                    HelperSendingObject.setObject(user);
                    try {
                        showChatPvPane();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                vbox_pv.getChildren().add(root);
                vbox_pv.getChildren().add(new Line(0, 0, 140, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void addToListGroup() throws SQLException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        vbox_group.getChildren().clear();
        for (Group group : dataBaseUser.getAlGroups(userCurrent.getId())) {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("custom_item_group.fxml"));
            try {
                HelperSendingObject.setObject(group);
                Parent root = loader.load();
                root.setOnMouseClicked(event -> {
                    HelperSendingObject.setObject(group);
                    try {
                        showChatGroupPane();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                vbox_group.getChildren().add(root);
                vbox_group.getChildren().add(new Line(0, 0, 140, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



