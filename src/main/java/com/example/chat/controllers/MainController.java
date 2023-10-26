package com.example.chat.controllers;

import com.example.chat.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    @FXML
    Pane chat_pane;

    @FXML
    ListView<String> listView_pv , listView_group;

    public void initialize() {
        chat_pane.setOnMouseClicked(mouseEvent -> {
            try {
                showChatPane();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        addToListPV();
        addToListGroup();

    }

    public void showChatPane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chat_page.fxml"));
        chat_pane.getChildren().add(fxmlLoader.load());
    }

    public void addToListPV() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Oveis");
        ObservableList<String> observableList = FXCollections.observableArrayList(strings);
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
                                if (loader[0]==null)
                                    System.out.println("Null");
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
    public void addToListGroup() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Oveis");
        ObservableList<String> observableList = FXCollections.observableArrayList(strings);

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
                                if (loader[0]==null)
                                    System.out.println("Null");
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



