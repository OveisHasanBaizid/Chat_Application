package com.example.chat.common;

public class ConverterPhoneNumbers {
    public static String convertIran(String phone) {
        String s = "";
        s += phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-"
                + phone.substring(6, 9) + "-" + phone.substring(9);
        return s;
    }

    public static String converterPhoneNumber(String prefixCode, String phone) {
        String changedPhone = "";
        switch (prefixCode) {
            case "+98" -> changedPhone = convertIran(prefixCode + phone);
        }
        return changedPhone;
    }
}
