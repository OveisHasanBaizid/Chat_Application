package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;

import java.io.IOException;

public class ListPvController {
    @FXML
    Text txt_name;
    public void initialize() throws IOException {
        String name = "(String) txt_name.getParent()";
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            txt_name.setText(name);
        }).start();
    }
}
