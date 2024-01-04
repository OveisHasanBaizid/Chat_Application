package com.example.chat.controllers;

import com.example.chat.Main;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.common.ShowDialog;
import com.example.chat.dataBase.DataBaseGroup;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.Group;
import com.example.chat.models.GroupMember;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class GroupDetailsController {
    @FXML
    Text txt_nameGroup, btn_back , number_users;
    @FXML
    Circle circle_image;

    Group group;

    @FXML
    VBox vbox_users;

    User userCurrent;

    DataBaseGroup dataBaseGroup;
    public void initialize() throws FileNotFoundException, SQLException {
        group = HelperSendingObject.getGroup();
        userCurrent = HelperSendingObject.getUserCurrent();

        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("images\\group_icon.jpeg"))));

        dataBaseGroup = new DataBaseGroup();
        number_users.setText("Users : "+dataBaseGroup.countMembers(group.getId()));

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
                Main.class.getResource("chat_group_page.fxml"));
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
        DataBaseUser dataBaseUser = new DataBaseUser();
        vbox_users.getChildren().clear();
        HelperSendingObject.setGroup(group);
        for (GroupMember member : dataBaseGroup.getAllGroupMembers(group.getId())) {
            User user = dataBaseUser.findUserById(member.getUserId());
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("custom_item_details_group.fxml"));
            try {
                new Thread(() -> {
                    HelperSendingObject.setObject(user);
                    HelperSendingObject.setIsAdmin(member.isThatMemberAdmin());
                }).start();
                Parent root = loader.load();
                root.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                        try {
                            if (user.getId() != userCurrent.getId()) {
                                HelperSendingObject.setIsAdmin(member.isThatMemberAdmin());
                                HelperSendingObject.setObject(user);
                                ShowDialog.showAlertRightClickOnUsers();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                vbox_users.getChildren().add(root);
                vbox_users.getChildren().add(new Line(0, 0, 500, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void btn_addUser() throws IOException {
        ShowDialog.showAlertAddToGroup();
    }
    public void btn_removeGroup() throws SQLException, IOException {
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        dataBaseGroup.removeUserOfGroup(group.getId(), userCurrent.getId());
        Pane pane = HelperSendingObject.getPaneChat();
        pane.getChildren().clear();

        Stage stage = (Stage) pane.getScene().getWindow();
        pane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 709, 523);
        stage.setTitle("Chat");
        stage.setScene(scene);
    }
}
