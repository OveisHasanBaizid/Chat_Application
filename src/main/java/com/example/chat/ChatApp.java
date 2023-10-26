package com.example.chat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Application");

        ListView<String> chatListView = new ListView<>();

        chatListView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    if (isRightMessage(item)) {
                        setStyle("-fx-alignment: center-right;");
                    } else {
                        setStyle("-fx-alignment: center-left;");
                    }
                }
            }
        });

        chatListView.getItems().addAll(
                "Hello",
                "Hi there!",
                "This is a sample chat.",
                "Right-aligned message.",
                "Left-aligned message."

        );

        VBox root = new VBox(chatListView);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        chatListView.scrollTo(chatListView.getItems().size() - 1);
    }

    private boolean isRightMessage(String message) {
        // Implement your logic to determine if the message is from the sender (right-aligned).
        // For example, you can check the sender's ID or some other criteria.
        return message.contains("Right-aligned");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
