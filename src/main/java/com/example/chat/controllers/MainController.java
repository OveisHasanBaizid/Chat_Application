package com.example.chat.controllers;

import com.example.chat.Main;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.common.PhoneNumberHelper;
import com.example.chat.common.ShowDialog;
import com.example.chat.dataBase.DataBaseGroup;
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
                new Image(new FileInputStream("images\\profile_1.jpeg"))));

        threadInitialize();

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

    public void threadInitialize(){
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
    }

    public void showChatPvPane() throws IOException {
        HelperSendingObject.setPaneChat(chat_pane);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("chat_pv_page.fxml"));
        chat_pane.getChildren().clear();
        chat_pane.getChildren().add(fxmlLoader.load());
    }

    public void showChatGroupPane() throws IOException {
        HelperSendingObject.setPaneChat(chat_pane);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("chat_group_page.fxml"));
        chat_pane.getChildren().clear();
        chat_pane.getChildren().add(fxmlLoader.load());
    }

    public void addToListPv() throws SQLException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        vbox_pv.getChildren().clear();
        for (User user : dataBaseUser.getAllContact(userCurrent.getId())) {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("custom_item_pv.fxml"));
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
                    Main.class.getResource("custom_item_group.fxml"));
            try {
                HelperSendingObject.setObject(group);
                Parent root = loader.load();
                root.setOnMouseClicked(event -> {
                    HelperSendingObject.setGroup(group);
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

    public void btn_addContact() throws SQLException {
        String phone = ShowDialog.showDialogGetOneInput("Add Contact" , "Phone");
        if (phone.length() < 10) {
            ShowDialog.showMessage("Error", "The phone entered is invalid.");
            return;
        }
        String prefix = phone.startsWith("+1") ? "+1" : phone.substring(0, 3);
        if (!PhoneNumberHelper.validationPhoneNumber(prefix,phone.substring(prefix.length()))){
            ShowDialog.showMessage("Error", "The phone entered is invalid.");
            return;
        }
        DataBaseUser dataBaseUser = new DataBaseUser();
        User user = dataBaseUser.findUserByPhone(PhoneNumberHelper
                .converterPhoneNumber(prefix,phone.substring(prefix.length())));
        System.out.println(prefix);
        System.out.println(phone);
        if (user==null){
            ShowDialog.showMessage("Error", "The phone entered not exist in chat application.");
        }else if (dataBaseUser.getAllContact(userCurrent.getId()).contains(user)){
            ShowDialog.showMessage("Error", "The phone entered exist in your contacts.");
        }else {
            dataBaseUser.addContact(userCurrent.getId(),user.getId());
            ShowDialog.showMessage("Info", "The phone entered added in your contacts successfully.");
            addToListPv();
        }
    }

    public void btn_addGroup() throws SQLException {
        String groupName = ShowDialog.showDialogGetOneInput("Add Group", "Name");
        if (groupName.length() < 3) {
            ShowDialog.showMessage("Error", "The name entered is invalid. The number of characters must be more than 3.");
            return;
        }
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        dataBaseGroup.addNewGroup(groupName);
        ShowDialog.showMessage("Info", "The new group added in your groups successfully.");
        addToListGroup();
    }
}



