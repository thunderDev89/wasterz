package com.acemen.android.wasterz.view.contribute.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.acemen.android.wasterz.view.contribute.Contribute;

/**
 * Created by Audrik ! on 27/03/2017.
 */

public class Utils {
    private static SharedPreferences prefs;

    /**
     * Get String preference value
     * @param context
     * @param prefName
     * @param PREFS_FILE_NAME
     * @return
     */
    public static String getStringPreferences(Context context, @Contribute.WasteParam String prefName, String PREFS_FILE_NAME) {
        prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        if (prefs.contains(prefName))
            return prefs.getString(prefName, null);
        else
            return "";
    }

    /**
     * set String Preference Value
     *
     * @param context
     * @param prefName Preference name
     * @param Value    Preference value
     */
    public static void setStringPreferences(Context context, @Contribute.WasteParam String prefName, String Value, String PREFS_FILE_NAME) {
        prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(prefName, Value);
        editor.apply();
    }
}
