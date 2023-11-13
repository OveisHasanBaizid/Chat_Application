package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseGroup;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.Group;
import com.example.chat.models.GroupMember;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class GroupDetailsController {
    @FXML
    Text txt_nameGroup, btn_back;
    @FXML
    Circle circle_image;

    Group group;

    @FXML
    VBox vbox_users;

    public void initialize() throws FileNotFoundException {
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_2.jpeg"))));
        group = (Group) HelperSendingObject.getObject();
        initialize2();

        btn_back.setOnMouseClicked(mouseEvent -> backToChat());
    }

    public void initialize2() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                txt_nameGroup.setText(group.getName());
                try {
                    addUsers();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();

    }

    public void backToChat() {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("chat_group_page.fxml"));
        try {
            HelperSendingObject.setObject(group);
            Pane pane = HelperSendingObject.getPaneChat();
            pane.getChildren().clear();
            pane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUsers() throws SQLException {
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        DataBaseUser dataBaseUser = new DataBaseUser();
        vbox_users.getChildren().clear();
        for (GroupMember member : dataBaseGroup.getAllGroupMembers(group.getId())) {
            User user = dataBaseUser.findUserById(member.getUserId());
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("custom_item_details_group.fxml"));
            try {
                new Thread(() -> {
                    HelperSendingObject.setObject(user);
                    HelperSendingObject.setIsAdmin(member.isThatMemberAdmin());
                }).start();
                Parent root = loader.load();
                vbox_users.getChildren().add(root);
                vbox_users.getChildren().add(new Line(0, 0, 500, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
