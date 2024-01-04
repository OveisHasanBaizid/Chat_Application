package com.example.chat;

import com.example.chat.dataBase.ConnectionDataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Connection connection = ConnectionDataBase.getConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Chat");
        stage.setResizable(false);
        stage.getIcons().add(new Image(new FileInputStream("images\\main_icon.jpeg")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}