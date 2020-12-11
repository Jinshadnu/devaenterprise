package com.vingcoz.devaenterprise.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    public static String LoginName = "LoginName";
    public static String LoginId = "LoginId";
    public static String LoggedIn = "LoggedIn";
    public static String MOBILE_NUMBER = "MobileNumber";
    public static String PIN = "Pin";
    public static String ADDRESS = "Address";
    public static String EMAIL = "Email";

    public static String PREF_LATITUDE = "PREF_LATITUDE";
    public static String PREF_LONGITUDE = "PREF_LONGITUDE";
    public static String PREF_MAP_ADDRESS = "PREF_MAP_ADDRESS";

    private final SharedPreferences MainPreference;
    private SharedPreferences.Editor editor;

    public PrefUtils(Context context) {
        MainPreference = context.getSharedPreferences(GlobalVariables.PACKAGE_NAME + "_preferences", Context.MODE_PRIVATE);
    }

    public String GetSharedString(String strKey) {
        return MainPreference.getString(strKey, "empty");
    }

    public void PutSharedString(String strKey, String strValue) {
        editor = MainPreference.edit();
        editor.putString(strKey, strValue);
        editor.apply();
    }

    public long GetSharedLong(String strKey) {
        return MainPreference.getLong(strKey, 0);
    }

    public void PutSharedLong(String strKey, long lngValue) {
        editor = MainPreference.edit();
        editor.putLong(strKey, lngValue);
        editor.apply();
    }

    public boolean GetSharedBool(String strKey) {
        return MainPreference.getBoolean(strKey, false);
    }

    public void PutSharedBool(String strKey, boolean blValue) {
        editor = MainPreference.edit();
        editor.putBoolean(strKey, blValue);
        editor.apply();
    }
}