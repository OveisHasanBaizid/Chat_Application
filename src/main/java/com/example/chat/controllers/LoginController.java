package com.example.chat.controllers;

import com.example.chat.Main;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.common.PhoneNumberHelper;
import com.example.chat.common.ShowDialog;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginController {
    @FXML
    TextField tf_phone, tf_prefixCode;

    @FXML
    ComboBox<String> cb_countries_name;

    public void initialize() {
        String[] countries_name = {"Australia", "Canada", "Egypt", "France", "Iran"};
        String[] countries_code = {"+61", "+1", "+20", "+33", "+98"};
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                cb_countries_name.setItems(FXCollections.observableList(List.of(countries_name)));
            });
            cb_countries_name.setOnAction(actionEvent -> {
                int index = cb_countries_name.getSelectionModel().getSelectedIndex();
                tf_prefixCode.setText(countries_code[index]);
            });
        }).start();

    }

    public void btnLogin() throws IOException, SQLException {
        String phone = tf_phone.getText();
        String prefixCode = tf_prefixCode.getText();

        if (phone.equals("admin")) {
            HelperSendingObject.setUserIsAdminSystem(true);
            showMainPage();
        }

        if (!PhoneNumberHelper.validationPhoneNumber(prefixCode, phone)) {
            ShowDialog.showMessage("Error", "The phone number entered is invalid.");
            return;
        }

        DataBaseUser dataBaseUser = new DataBaseUser();
        phone = PhoneNumberHelper.converterPhoneNumber(prefixCode, phone);
        User user = dataBaseUser.findUserByPhone(phone);
        if (user == null) {
            if (ShowDialog.showAlertRegistrationQuestion())
                register();
        } else {
            HelperSendingObject.setUserCurrent(user);
            showMainPage();
        }
    }

    public void register() throws SQLException, IOException {
        String phone = PhoneNumberHelper.converterPhoneNumber(tf_prefixCode.getText(), tf_phone.getText());
        DataBaseUser dataBaseUser = new DataBaseUser();
        String name = ShowDialog.showDialogGetOneInput("Get Your UserName" , "Username");
        while (name.isEmpty() || dataBaseUser.isExistUserName(name)) {
            ShowDialog.showMessage("Error", "The name entered is invalid.");
            name = ShowDialog.showDialogGetOneInput("Get Your UserName" , "Username");
        }
        User user = new User(0, 0, name, phone, false);
        dataBaseUser.insertUser(user, tf_prefixCode.getText());
        ShowDialog.showMessage("Info", "Registration was successful.");
        HelperSendingObject.setUserCurrent(user);
        showMainPage();
    }

    public void showMainPage() throws IOException {
        Stage stage = (Stage) tf_phone.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 709, 523);
        stage.setTitle("Chat");
        stage.setScene(scene);
    }
}
