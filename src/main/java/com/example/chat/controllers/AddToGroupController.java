package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseGroup;
import com.example.chat.models.Group;
import com.example.chat.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddToGroupController {
    @FXML
    DialogPane dialog_pane;

    @FXML
    VBox vBox_list;

    User userCurrent;

    Group group;

    public void initialize() throws SQLException {
        group = HelperSendingObject.getGroup();
        userCurrent = HelperSendingObject.getUserCurrent();

        new Thread(() -> {
            try {
                initVBox();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void initVBox() throws SQLException {
        DataBaseGroup dataBaseGroup = new DataBaseGroup();
        for (User user : dataBaseGroup.filterUserAreNotInGroup(group.getId())) {
            Text text = new Text(" " + user.getName() + "   " + user.getPhone());
            text.setFont(Font.font(17));
            vBox_list.getChildren().add(text);
            vBox_list.getChildren().add(new Line(20, 5, 220, 5));
            text.setOnMouseClicked(mouseEvent -> {
                try {
                    dataBaseGroup.addUserToGroup(group.getId(), user.getId());
                    showChatGroupPane();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
                btn_back();
            });
        }
    }

    public void showChatGroupPane() throws IOException {
        HelperSendingObject.setGroup(group);
        Pane pane = HelperSendingObject.getPaneChat();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("group_details_page.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(fxmlLoader.load());
    }

    public void btn_back() {
        Stage stage = (Stage) dialog_pane.getScene().getWindow();
        stage.close();
    }
}
