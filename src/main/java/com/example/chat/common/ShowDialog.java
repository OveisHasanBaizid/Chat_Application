package com.example.chat.common;

import com.example.chat.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ShowDialog {
    public static void showMessage(String type, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (type.equals("Error"))
            alert.setAlertType(Alert.AlertType.ERROR);
        alert.getDialogPane().setPrefSize(300, 100);
        alert.setTitle(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAlertRightClickOnUsers() throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("dialog_pane_right_click_on_users.fxml"));
        DialogPane dialogPane = loader.load();
        alert.setDialogPane(dialogPane);
        alert.setTitle("Details");
        alert.getDialogPane().setPrefSize(200, 265);
        alert.showAndWait();
    }

    public static void showAlertAddToGroup() throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("dialog_pane_add_to_group.fxml"));
        DialogPane dialogPane = loader.load();
        alert.setTitle("Add to group");
        alert.setDialogPane(dialogPane);
        alert.getDialogPane().setPrefSize(223, 323);
        alert.showAndWait();
    }

    public static boolean showAlertRegistrationQuestion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert Registration Question");
        alert.setHeaderText(null);
        alert.setContentText("User not found. Do you want to register?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public static String showDialogForGetUserName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Your UserName");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your username :");

        AtomicReference<String> name = new AtomicReference<>("");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(answer -> {
            name.set(answer);
        });
        return name.get();
    }
}
