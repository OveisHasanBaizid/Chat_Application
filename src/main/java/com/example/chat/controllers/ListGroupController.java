package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.models.Group;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class ListGroupController {
    @FXML
    Text txt_nameGroup, txt_nUser;

    @FXML
    Circle circle_image;

    Group group;

    public void initialize() throws IOException {
        group = (Group) HelperSendingObject.getObject();

        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_2.jpeg"))));

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (group != null) {
                txt_nameGroup.setText(group.getName());
                txt_nUser.setText("0");
            }
        }).start();
    }

}
