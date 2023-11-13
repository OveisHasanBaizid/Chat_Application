package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ItemDetailsGroupController {
    @FXML
    Text txt_name, txt_phone, txt_admin;

    @FXML
    Circle circle_image;

    User user, currentUser;

    boolean isAdmin;

    public void initialize() throws FileNotFoundException {
        user = (User) HelperSendingObject.getObject();
        currentUser = HelperSendingObject.getUserCurrent();
        isAdmin = HelperSendingObject.isIsAdmin();
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_1.jpeg"))));

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                if (user.getId() == currentUser.getId()) {
                    txt_name.setText("You");
                    txt_name.setFill(Color.GREEN);
                } else
                    txt_name.setText(user.getName());
                if (!isAdmin)
                    txt_admin.setText("");
                txt_phone.setText(user.getPhone());
            });
        }).start();
    }
}
