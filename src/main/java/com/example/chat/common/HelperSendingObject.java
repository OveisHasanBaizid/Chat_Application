package com.example.chat.common;

import com.example.chat.models.Group;
import com.example.chat.models.User;
import javafx.scene.layout.Pane;

public final class HelperSendingObject {
    private static Object object;
    private static User userCurrent;
    private static Pane paneChat;
    private static boolean isAdmin;
    private static Group group;
    private static boolean userIsAdminSystem;

    public static boolean isUserIsAdminSystem() {
        return userIsAdminSystem;
    }

    public static void setUserIsAdminSystem(boolean userIsAdminSystem) {
        HelperSendingObject.userIsAdminSystem = userIsAdminSystem;
    }

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

    public static Pane getPaneChat() {
        return paneChat;
    }

    public static void setPaneChat(Pane paneChat) {
        HelperSendingObject.paneChat = paneChat;
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        HelperSendingObject.isAdmin = isAdmin;
    }

    public static Group getGroup() {
        return group;
    }

    public static void setGroup(Group group) {
        HelperSendingObject.group = group;
    }
}
