package com.example.chat.models;

public class Country {
    private int id;
    private String name;
    private String preNumber;

    public Country(int id, String name, String preNumber) {
        this.id = id;
        this.name = name;
        this.preNumber = preNumber;
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

    public String getPreNumber() {
        return preNumber;
    }

    public void setPreNumber(String preNumber) {
        this.preNumber = preNumber;
    }
}
