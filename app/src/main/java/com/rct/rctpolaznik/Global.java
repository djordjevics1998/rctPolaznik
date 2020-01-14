package com.rct.rctpolaznik;

import android.content.Context;
import android.content.Intent;

public class Global {
    public static final String PACKAGE_NAME = ".com.rctpolaznik.",
            PACKAGE_WORKER = PACKAGE_NAME + "workers.",
            PACKAGE_DATA = PACKAGE_NAME + "data.",
            ACTION_CHANGE_FRAGMENT = PACKAGE_NAME + "action.change_fragment",
            ACTION_DISPLAY_MESSAGE = PACKAGE_NAME + "action.display_message",
            ARG_TAG_FRAGMENT = PACKAGE_NAME + "argument.tag_fragment",
            ARG_POP = PACKAGE_NAME + "argument.pop",
            ARG_MESSAGE = PACKAGE_NAME + "argument.message",
            NULL_STRING = "null",
            ARG = PACKAGE_NAME + "arg.",
            ARG_ID_COMPANY = ARG + "id_company",
            ARG_COMPANY = ARG + "company",
            ARG_PROMOTION = ARG + "promotion";
    public static final int NULL_INT = -1;
    public static final double NULL_DOUBLE = -1;

    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(ACTION_DISPLAY_MESSAGE);
        intent.putExtra(ARG_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
