module com.example.chat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example.chat to javafx.fxml;
    opens com.example.chat.controllers to javafx.fxml;

    exports com.example.chat;
    exports com.example.chat.controllers;
}