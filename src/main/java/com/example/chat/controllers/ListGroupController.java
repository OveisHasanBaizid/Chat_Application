package com.example.chat.controllers;

import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseGroup;
import com.example.chat.models.Group;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ListGroupController {
    @FXML
    Text txt_nameGroup, txt_nUser;

    @FXML
    Circle circle_image;

    Group group;

    public void initialize() throws IOException {
        group = (Group) HelperSendingObject.getObject();

        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("images\\group_icon.jpeg"))));

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (group != null) {
                txt_nameGroup.setText(group.getName());

                DataBaseGroup dataBaseGroup = new DataBaseGroup();
                try {
                    txt_nUser.setText(String.valueOf(dataBaseGroup.countMembers(group.getId())));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
