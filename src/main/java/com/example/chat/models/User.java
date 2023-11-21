package com.example.chat.models;

public class User {
    private int id;
    private int countryId;
    private String name;
    private String phone;
    private boolean blocked;

    public User(int id, int countryId, String name, String phone, boolean blocked) {
        this.id = id;
        this.countryId = countryId;
        this.name = name;
        this.phone = phone;
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

}
