package com.example.chat.model;
import java.time.LocalDate;
public class MessagePrivate {
    private int id;
    private int senderId;
    private LocalDate sendingDateTime;
    private String body;
    private int userId;

    public MessagePrivate(int id, int senderId, LocalDate sendingDateTime, String body, int userId) {
        this.id = id;
        this.senderId = senderId;
        this.sendingDateTime = sendingDateTime;
        this.body = body;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public LocalDate getSendingDateTime() {
        return sendingDateTime;
    }

    public void setSendingDateTime(LocalDate sendingDateTime) {
        this.sendingDateTime = sendingDateTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
