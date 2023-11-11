package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.common.ConverterPhoneNumbers;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.common.ShowDialog;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    TextField tf_phone, tf_prefixCode;

    public void btnLogin() throws IOException, SQLException {
        String phone = tf_phone.getText();
        String prefixCode = tf_prefixCode.getText();
        if (phone.length() != 10) {
            ShowDialog.showMessage("Error", "The phone number entered is invalid.");
            return;
        }
        DataBaseUser dataBaseUser = new DataBaseUser();
        phone = ConverterPhoneNumbers.converterPhoneNumber(prefixCode, phone);
        User user = dataBaseUser.findUserByPhone(phone);
        if (user == null) {
            ShowDialog.showMessage("Error", "The desired user was not found.");
        } else {
            HelperSendingObject.setUserCurrent(user);
            showMainPage();
        }
    }

    public void showMainPage() throws IOException {
        Stage stage = (Stage) tf_phone.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 709, 523);
        stage.setTitle("Chat");
        stage.setScene(scene);
    }
}
