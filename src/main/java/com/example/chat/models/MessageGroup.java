package com.example.chat.models;
import java.time.LocalDate;

public class MessageGroup {
    private int id;
    private int senderId;
    private LocalDate sendingDateTime;
    private String body;
    private int groupId;

    public MessageGroup(int id, int senderId, LocalDate sendingDateTime
            , String body, int groupId) {
        this.id = id;
        this.senderId = senderId;
        this.sendingDateTime = sendingDateTime;
        this.body = body;
        this.groupId = groupId;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
