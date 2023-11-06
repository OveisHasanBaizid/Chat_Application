package com.example.chat.common;

public final class HelperSendingObject {
    private static Object object;

    public static Object getObject() {
        return object;
    }

    public static void setObject(Object object) {
        HelperSendingObject.object = object;
    }
}
