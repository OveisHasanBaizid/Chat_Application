package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import com.example.chat.dataBase.DataBaseUser;
import com.example.chat.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {
    @FXML
    Pane chat_pane;

    @FXML
    ListView<String> listView_pv, listView_group;

    @FXML
    TextField tf_name;

    User user;
    public void initialize() {
        chat_pane.setOnMouseClicked(mouseEvent -> {
            try {
                showChatPane();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


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
                addToListGroup();
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
        ObservableList<String> observableList = FXCollections
                .observableArrayList(dataBaseUser.getAllContact(user.getName()));
        listView_pv.setItems(observableList);

        listView_pv.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                final FXMLLoader[] loader = {null};
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if (b || s == null) {
                            setGraphic(null);
                        } else {
                            if (loader[0] == null) {
                                loader[0] = new FXMLLoader(HelloApplication.class.getResource("custom_item_pv.fxml"));
                                try {
                                    Parent root = loader[0].load();
                                    tf_name.getScene().getWindow().setUserData(s);
                                    root.setUserData(s);
                                    setGraphic(root);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
                final FXMLLoader[] loader = {null};
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if (b || s == null) {
                            setGraphic(null);
                        } else {
                            if (loader[0] == null) {
                                loader[0] = new FXMLLoader(HelloApplication.class.getResource("custom_item_group.fxml"));
                                try {
                                    Parent root = loader[0].load();
                                    setGraphic(root);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                };
            }
        });
    }
}



