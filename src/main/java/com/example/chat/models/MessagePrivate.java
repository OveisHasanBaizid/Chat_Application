package com.example.chat.models;
import java.time.LocalDate;
public class MessagePrivate {
    private int id;
    private int senderId;
    private LocalDate sendingDateTime;
    private String body;
    private int receiverUserId;

    public MessagePrivate(int id, int senderId, LocalDate sendingDateTime
            , String body, int receiverUserId) {
        this.id = id;
        this.senderId = senderId;
        this.sendingDateTime = sendingDateTime;
        this.body = body;
        this.receiverUserId = receiverUserId;
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

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }
}
