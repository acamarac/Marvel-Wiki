package es.unex.asee.proyectoasee.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.android.proyectoasee.R;

import es.unex.asee.proyectoasee.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String KEY_PREF_LIMIT = "limit_api";
    public static final String KEY_PREF_LOAD_IMAGES = "load_images";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Settings");
        setPreferencesFromResource(R.xml.preferences, s);
    }
}
