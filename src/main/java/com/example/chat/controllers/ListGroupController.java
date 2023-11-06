package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class ListGroupController {
    @FXML
    Text txt_nameGroup;

    @FXML
    Circle circle_image;

    public void initialize() throws IOException {
        String name = (String) HelperSendingObject.getObject();
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_2.jpeg"))));

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            txt_nameGroup.setText(name);
        }).start();
    }
}
