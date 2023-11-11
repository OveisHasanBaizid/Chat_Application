package com.example.chat.models;

import java.time.LocalDate;

public class Group {
    private int id;
    private String name;
    private int creatorId;
    private LocalDate creationDate;

    public Group(int id, String name, int creatorId, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.creatorId = creatorId;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
