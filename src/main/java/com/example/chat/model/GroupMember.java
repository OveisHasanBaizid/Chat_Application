package com.example.chat.model;

public class GroupMember {
    private int memberId;
    private int groupId;
    private int userId;
    private boolean isThatMemberAdmin;

    public GroupMember(int memberId, int groupId, int userId, boolean isThatMemberAdmin) {
        this.memberId = memberId;
        this.groupId = groupId;
        this.userId = userId;
        this.isThatMemberAdmin = isThatMemberAdmin;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isThatMemberAdmin() {
        return isThatMemberAdmin;
    }

    public void setThatMemberAdmin(boolean thatMemberAdmin) {
        isThatMemberAdmin = thatMemberAdmin;
    }
}
