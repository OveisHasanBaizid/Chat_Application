package com.example.chat.common;

public class PhoneNumberHelper {
    public static String convertIran(String phone) {
        return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-"
                + phone.substring(6, 9) + "-" + phone.substring(9);
    }
    public static String convertAustralia(String phone) {
        return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-"
                + phone.substring(6, 9) + "-" + phone.substring(9);
    }
    public static String convertCanada(String phone) {
        return phone.substring(0, 2) + "-" + phone.substring(2, 5) + "-"
                + phone.substring(5, 8) + "-" + phone.substring(8);
    }
    public static String convertEgypt(String phone) {
        return phone.substring(0, 3) + "-" + phone.substring(3, 5) + "-"
                + phone.substring(5, 9) + "-" + phone.substring(9);
    }
    public static String convertFrance(String phone) {
        return phone.substring(0, 3) + "-" + phone.substring(3, 4) + "-"
                + phone.substring(4, 6) + "-" + phone.substring(6,8)
                + "-" + phone.substring(8,10)+ "-" + phone.substring(10,12);
    }
    public static String converterPhoneNumber(String prefixCode, String phone) {
        String changedPhone = "";
        switch (prefixCode) {
            case "+98" -> changedPhone = convertIran(prefixCode + phone);
            case "+1" -> changedPhone = convertCanada(prefixCode + phone);
            case "+61" -> changedPhone = convertAustralia(prefixCode + phone);
            case "+20" -> changedPhone = convertEgypt(prefixCode + phone);
            case "+33" -> changedPhone = convertFrance(prefixCode + phone);
        }
        return changedPhone;
    }
    public static boolean validationPhoneNumber(String prefixCode, String phone) {
        boolean validation = true;
        switch (prefixCode) {
            case "+98", "+1", "+20" -> validation = phone.length()==10;
            case "+61", "+33" -> validation = phone.length()==9;
        }
        return validation;
    }
}
