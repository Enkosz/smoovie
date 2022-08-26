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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unimib.smoovie.R;
import it.unimib.smoovie.viewmodel.UserViewModel;

public class SettingsFragment extends Fragment {

    private TextView textViewProfileUsername;
    private TextView textViewNotificationsSettings;
    private SwitchCompat switchShowMatureContent;
    private SwitchCompat switchDarkMode;

    private RelativeLayout relativeLayoutShowMatureContent;
    private RelativeLayout relativeLayoutLanguageSettings;
    private RelativeLayout relativeLayoutDarkMode;

    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        textViewProfileUsername = view.findViewById(R.id.textView_settings_profile_username);
        textViewNotificationsSettings = view.findViewById(R.id.notifications_settings);
        switchShowMatureContent = view.findViewById(R.id.switch_show_mature_content);
        relativeLayoutShowMatureContent = view.findViewById(R.id.relative_layout_show_mature_content);
        relativeLayoutLanguageSettings = view.findViewById(R.id.relative_layout_language);
        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        relativeLayoutDarkMode = view.findViewById(R.id.relative_layout_dark_mode);

        setupViewModel();
        setupNavigation();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void setupNavigation() {
        textViewNotificationsSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.notificationsFragment));

        relativeLayoutLanguageSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.languageFragment));
    }

    private void setupUI() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

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

        String keytag1 = "darkMode";
        switchDarkMode.setChecked(sharedPreferences.getBoolean(keytag1, true));
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

    }
}
