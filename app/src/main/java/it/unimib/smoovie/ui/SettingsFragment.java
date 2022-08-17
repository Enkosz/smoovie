package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SwitchCompat switchShowMatureContent = view.findViewById(R.id.switch_show_mature_content);
        switchShowMatureContent.setChecked(sharedPreferences.getBoolean("showMatureContent", true));
        switchShowMatureContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("showMatureContent", true);
                    Log.i("switch_show_mature_content", "true");
                } else {
                    editor.putBoolean("showMatureContent", false);
                    Log.i("switch_show_mature_content", "false");
                }
                editor.apply();
            }
        });
        RelativeLayout relativeLayoutShowMatureContent = view.findViewById(R.id.relative_layout_show_mature_content);
        relativeLayoutShowMatureContent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sharedPreferences.getBoolean("showMatureContent", true)) {
                            switchShowMatureContent.setChecked(false);
                            editor.putBoolean("showMatureContent", false).apply();
                        } else {
                            switchShowMatureContent.setChecked(true);
                            editor.putBoolean("showMatureContent", true).apply();
                        }
                    }
                }
        );

        String keytag1 = "darkMode";
        SwitchCompat switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        switchDarkMode.setChecked(sharedPreferences.getBoolean(keytag1, true));
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(keytag1, true);
                    Log.i(keytag1, "true");
                } else {
                    editor.putBoolean(keytag1, false);
                    Log.i(keytag1, "false");
                }
                editor.apply();
            }
        });

        RelativeLayout relativeLayoutDarkMode  = view.findViewById(R.id.relative_layout_dark_mode);
        relativeLayoutDarkMode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sharedPreferences.getBoolean(keytag1, true)) {
                            switchDarkMode.setChecked(false);
                            editor.putBoolean(keytag1, false).apply();
                        } else {
                            switchDarkMode.setChecked(true);
                            editor.putBoolean(keytag1, true).apply();
                        }
                    }
                }
        );

        return view;
    }
}
