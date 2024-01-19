package com.example.chat.controllers;

import com.example.chat.Main;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseGroup;
import com.example.chat.models.Group;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RightClickUsersController {
    @FXML
    DialogPane dialog_pane;
    User user, userCurrent;
    Group group;
    @FXML
    Button btn_upgrade , btn_remove;
    @FXML
    Text nameUser;

    boolean isAdmin;
    public void initialize(){
        user = (User) HelperSendingObject.getObject();
        userCurrent = HelperSendingObject.getUserCurrent();
        group = HelperSendingObject.getGroup();
        isAdmin = HelperSendingObject.isIsAdmin();
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isAdmin)
                btn_upgrade.setDisable(true);
            if (isAdmin && group.getCreatorId()!=userCurrent.getId())
                btn_remove.setDisable(true);
            nameUser.setText("User : "+user.getName());
        }).start();
    }
    public void btn_back(){
        Stage stage = (Stage) dialog_pane.getScene().getWindow();
        stage.close();
    }
    public void btn_removeUser() throws SQLException, IOException {
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        dataBaseGroup.removeUserOfGroup(group.getId(),user.getId());

        btn_back();

        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("group_details_page.fxml"));
            HelperSendingObject.setGroup(group);
            Pane pane = HelperSendingObject.getPaneChat();
            pane.getChildren().clear();
            pane.getChildren().add(loader.load());
    }
    public void btn_sendMessage(){
        Stage stage = (Stage) dialog_pane.getScene().getWindow();
        stage.close();
    }
}
