package com.example.chat.models;

public class BlockedUser {
    private int groupListId;
    private String userName;
    private int requester;

    public BlockedUser(int groupListId, String userName, int requester) {
        this.groupListId = groupListId;
        this.userName = userName;
        this.requester = requester;
    }

    public int getGroupListId() {
        return groupListId;
    }

    public void setGroupListId(int groupListId) {
        this.groupListId = groupListId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRequester() {
        return requester;
    }

    public void setRequester(int requester) {
        this.requester = requester;
    }
}
