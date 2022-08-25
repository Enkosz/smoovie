package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import it.unimib.smoovie.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView textViewNotificationsSettings = view.findViewById(R.id.notifications_settings);
        textViewNotificationsSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.notificationsFragment));

        RelativeLayout relativeLayoutLanguageSettings = view.findViewById(R.id.relative_layout_language);
        relativeLayoutLanguageSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.languageFragment));

        TextView textViewLanguage = view.findViewById(R.id.textView_language);
        textViewLanguage.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.languageFragment));

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String TAG_darkMode = "darkMode";
        String TAG_showMatureContent = "showMatureContent";

        if (sharedPreferences.getBoolean(TAG_darkMode, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        SwitchCompat switchShowMatureContent = view.findViewById(R.id.switch_show_mature_content);
        switchShowMatureContent.setChecked(sharedPreferences.getBoolean("showMatureContent", true));
        switchShowMatureContent.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                editor.putBoolean(TAG_showMatureContent, true).apply();
                Log.i(TAG_showMatureContent, "true");
            } else {
                editor.putBoolean(TAG_showMatureContent, false).apply();
                Log.i(TAG_showMatureContent, "false");
            }
        });
        RelativeLayout relativeLayoutShowMatureContent = view.findViewById(R.id.relative_layout_show_mature_content);
        relativeLayoutShowMatureContent.setOnClickListener(
                view1 -> {
                    if (sharedPreferences.getBoolean(TAG_showMatureContent, true)) {
                        switchShowMatureContent.setChecked(false);
                        editor.putBoolean(TAG_showMatureContent, false).apply();
                    } else {
                        switchShowMatureContent.setChecked(true);
                        editor.putBoolean(TAG_showMatureContent, true).apply();
                    }
                }
        );
        TextView textViewShowMatureContent = view.findViewById(R.id.textView_show_mature_content);
        textViewShowMatureContent.setOnClickListener(
                view12 -> {
                    if (sharedPreferences.getBoolean(TAG_showMatureContent, true)) {
                        switchShowMatureContent.setChecked(false);
                        editor.putBoolean(TAG_showMatureContent, false).apply();
                    } else {
                        switchShowMatureContent.setChecked(true);
                        editor.putBoolean(TAG_showMatureContent, true).apply();
                    }
                }
        );

        SwitchCompat switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        switchDarkMode.setChecked(sharedPreferences.getBoolean(TAG_darkMode, false));
        switchDarkMode.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                editor.putBoolean(TAG_darkMode, true).apply();
                Log.i(TAG_darkMode, "true");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                editor.putBoolean(TAG_darkMode, false).apply();
                Log.i(TAG_darkMode, "false");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        RelativeLayout relativeLayoutDarkMode = view.findViewById(R.id.relative_layout_dark_mode);
        relativeLayoutDarkMode.setOnClickListener(
                view2 -> {
                    if (sharedPreferences.getBoolean(TAG_darkMode, true)) {
                        switchDarkMode.setChecked(false);
                        editor.putBoolean(TAG_darkMode, false).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        switchDarkMode.setChecked(true);
                        editor.putBoolean(TAG_darkMode, true).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
        );
        TextView textViewDarkMode = view.findViewById(R.id.textView_dark_mode);
        textViewDarkMode.setOnClickListener(
                view22 -> {
                    if (sharedPreferences.getBoolean(TAG_darkMode, true)) {
                        switchDarkMode.setChecked(false);
                        editor.putBoolean(TAG_darkMode, false).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        switchDarkMode.setChecked(true);
                        editor.putBoolean(TAG_darkMode, true).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
        );

        return view;
    }
}
