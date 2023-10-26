package com.example.chat.models;

public class ContactList {
    private int id;
    private int mainUserId;
    private int userInContractListId;

    public ContactList(int id, int mainUserId, int userInContractListId) {
        this.id = id;
        this.mainUserId = mainUserId;
        this.userInContractListId = userInContractListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainUserId() {
        return mainUserId;
    }

    public void setMainUserId(int mainUserId) {
        this.mainUserId = mainUserId;
    }

    public int getUserInContractListId() {
        return userInContractListId;
    }

    public void setUserInContractListId(int userInContractListId) {
        this.userInContractListId = userInContractListId;
    }
}
