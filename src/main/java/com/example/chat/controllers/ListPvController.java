package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class ListPvController {
    @FXML
    Text txt_name , txt_phone;

    @FXML
    Circle circle_image;

    User user;
    public void initialize() throws IOException {
        user = (User) HelperSendingObject.getObject();

        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_2.jpeg"))));

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (user!=null){
                txt_name.setText(user.getName());
                txt_phone.setText(user.getPhone());
            }
        }).start();
    }

}
