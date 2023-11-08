package com.example.chat.common;

import com.example.chat.models.User;

public final class HelperSendingObject {
    private static Object object;
    private static User userCurrent;
    public static Object getObject() {
        return object;
    }
    public static void setObject(Object object) {
        HelperSendingObject.object = object;
    }

    public static User getUserCurrent() {
        return userCurrent;
    }

    public static void setUserCurrent(User userCurrent) {
        HelperSendingObject.userCurrent = userCurrent;
    }
}
