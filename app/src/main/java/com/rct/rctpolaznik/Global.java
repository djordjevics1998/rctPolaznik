package com.rct.rctpolaznik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.VolleyError;
import com.rct.rctpolaznik.fragments.Login;
import com.rct.rctpolaznik.fragments.MainMenu;
import com.rct.rctpolaznik.fragments.dialogs.NotLoggedIn;

public class Global {
    public static final String PACKAGE_NAME = ".com.rctpolaznik.",
            PACKAGE_WORKER = PACKAGE_NAME + "workers.",
            PACKAGE_DATA = PACKAGE_NAME + "data.",
            PACKAGE_FRAGMENTS = PACKAGE_NAME + "fragments.",
            ACTION_CHANGE_FRAGMENT = PACKAGE_NAME + "action.change_fragment",
            ACTION_DISPLAY_MESSAGE = PACKAGE_NAME + "action.display_message",
            ARG_TAG_FRAGMENT = PACKAGE_NAME + "argument.tag_fragment",
            ARG_POP = PACKAGE_NAME + "argument.pop",
            ARG_MESSAGE = PACKAGE_NAME + "argument.message",
            TAG_FRAGMENT_MAIN_MENU = PACKAGE_FRAGMENTS + "main_menu",
            TAG_FRAGMENT_LOGIN = PACKAGE_FRAGMENTS + "login",
            TAG_FRAGMENT_NOT_LOGGED_IN = PACKAGE_FRAGMENTS + "not_logged_in",
            NULL_STRING = "null",
            ARG = PACKAGE_NAME + "arg.",
            ARG_EXAMPLE1 = ARG + "EXAMPLE1",
            ARG_EXAMPLE2 = ARG + "EXAMPLE2";
    public static final int NULL_INT = -1;
    public static final double NULL_DOUBLE = -1;

    public static void changeFragment(FragmentActivity activity, String tag_fragment, boolean pop, Bundle args)
    {
        Fragment fragment;
        switch (tag_fragment) {
            case TAG_FRAGMENT_MAIN_MENU:
                fragment = MainMenu.newInstance(args);
                break;
            case TAG_FRAGMENT_LOGIN:
                fragment = Login.newInstance(args);
                break;
            case TAG_FRAGMENT_NOT_LOGGED_IN:
                fragment = NotLoggedIn.newInstance(args);
                break;
            default:
                return;
        }
        if(pop && activity.getSupportFragmentManager().getBackStackEntryCount() > 0) activity.getSupportFragmentManager().popBackStack();
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, fragment, tag_fragment);
        fragmentTransaction.addToBackStack(tag_fragment);
        fragmentTransaction.commit();
    }

    public static void changeFragment(Context context, String tagFragment, boolean pop, Bundle bundle) {
        Intent intent = new Intent(ACTION_CHANGE_FRAGMENT);
        intent.putExtra(ARG_TAG_FRAGMENT, tagFragment);
        intent.putExtra(ARG_POP, pop);
        if(bundle != null) intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(ACTION_DISPLAY_MESSAGE);
        intent.putExtra(ARG_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static String getErrorMessage(VolleyError error) {
        if(error != null) {
            String err = "Error: " + error.toString();
            if(error.networkResponse != null) err += " error code:" + error.networkResponse.statusCode;
            return err;
        } else return "Unknown error";
    }
}
