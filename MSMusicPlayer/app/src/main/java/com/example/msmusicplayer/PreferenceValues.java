package com.example.msmusicplayer;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceValues {
    private final Context context;

    public PreferenceValues(Context context) {
        this.context = context;
    }

    public void updatePreference(String key, String value)
    {
        SharedPreferences preferences=context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String retrivePreference(String key) {
        SharedPreferences preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return preferences.getString(key, "NOT_FOUND");
    }
}
