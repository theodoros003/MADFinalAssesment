package com.example.theodoros.madfinalassesment;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class Preference extends PreferenceActivity
{
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
