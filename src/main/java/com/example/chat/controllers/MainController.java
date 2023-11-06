package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.common.HelperSendingObject;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    Pane chat_pane;

    @FXML
    ListView<User> listView_pv;
    ListView<String> listView_group;

    @FXML
    TextField tf_name;

    User user;

    @FXML
    Circle circle_image;

    public void initialize() throws FileNotFoundException {
        circle_image.setFill(new ImagePattern(
                new Image(new FileInputStream("C:\\Users\\Oveis\\IdeaProjects\\Chat\\images\\profile_1.jpeg"))));
        actionListPV();

        threadStart();

    }

    public void threadStart() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            user = (User) chat_pane.getScene().getWindow().getUserData();
            tf_name.setText(user.getName());
            try {
                addToListPv();
            //    addToListGroup();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void showChatPane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat_page.fxml"));
        chat_pane.getChildren().add(fxmlLoader.load());
    }

    public void addToListPv() throws SQLException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        ObservableList<User> observableList = FXCollections
                .observableArrayList(dataBaseUser.getAllContact(user.getName()));
        listView_pv.setItems(observableList);
        listView_pv.setCellFactory(new Callback<>() {
            @Override
            public ListCell<User> call(ListView<User> userListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(User user, boolean b) {
                        super.updateItem(user, b);
                        if (b || user == null) {
                            setGraphic(null);
                        } else {
                            FXMLLoader loader = new FXMLLoader(
                                    HelloApplication.class.getResource("custom_item_pv.fxml"));
                            try {
                                Parent root = loader.load();
                                HelperSendingObject.setObject(user);
                                setGraphic(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
    }

    public void addToListGroup() throws SQLException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        ObservableList<String> observableList = FXCollections
                .observableArrayList(dataBaseUser.getAlGroups(user.getName()));
        listView_group.setItems(observableList);
        listView_group.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if (b || s == null) {
                            setGraphic(null);
                        } else {
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("custom_item_group.fxml"));
                            try {
                                Parent root = loader.load();
                                HelperSendingObject.setObject(s);
                                setGraphic(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
    }

    public void actionListPV() {
        listView_pv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView_pv.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            System.out.println(t1);
            try {
                showChatPane();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}



