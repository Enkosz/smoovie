package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import it.unimib.smoovie.R;
import it.unimib.smoovie.firebase.AuthManager;

public class SettingsFragment extends Fragment {

    private TextView textViewProfileUsername;
    private TextView textViewNotificationsSettings;
    private TextView textViewShowMatureContent;
    private TextView textViewPreferences;
    private TextView textViewSettingsLogout;
    private SwitchCompat switchShowMatureContent;

    private RelativeLayout relativeLayoutShowMatureContent;
    private RelativeLayout relativeLayoutLanguageSettings;
    private TextView textViewLanguageSettings;

    private AuthManager authManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        textViewProfileUsername = view.findViewById(R.id.textView_settings_profile_username);
        textViewNotificationsSettings = view.findViewById(R.id.notifications_settings);
        textViewShowMatureContent = view.findViewById(R.id.textView_show_mature_content);
        textViewSettingsLogout = view.findViewById(R.id.textView_settings_logout);
        switchShowMatureContent = view.findViewById(R.id.switch_show_mature_content);
        relativeLayoutShowMatureContent = view.findViewById(R.id.relative_layout_show_mature_content);
        relativeLayoutLanguageSettings = view.findViewById(R.id.relative_layout_language);
        textViewLanguageSettings = view.findViewById(R.id.textView_language);
        textViewPreferences = view.findViewById(R.id.settings_preferences);

        authManager = AuthManager.getInstance(requireActivity().getApplication());

        setupNavigation();
        setupUI();
        setupLogoutHandler();
        return view;
    }

    private void setupNavigation() {
        textViewNotificationsSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.notificationsFragment));

        relativeLayoutLanguageSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.languageFragment));

        textViewLanguageSettings.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.languageFragment));

        textViewPreferences.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.preferencesFragment));
    }

    private void setupUI() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switchShowMatureContent.setChecked(sharedPreferences.getBoolean("showMatureContent", true));
        switchShowMatureContent.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                editor.putBoolean("showMatureContent", true).apply();
            } else {
                editor.putBoolean("showMatureContent", false).apply();
            }
        });
        relativeLayoutShowMatureContent.setOnClickListener(
                view1 -> {
                    if (sharedPreferences.getBoolean("showMatureContent", true)) {
                        switchShowMatureContent.setChecked(false);
                        editor.putBoolean("showMatureContent", false).apply();
                    } else {
                        switchShowMatureContent.setChecked(true);
                        editor.putBoolean("showMatureContent", true).apply();
                    }
                }
        );
        textViewShowMatureContent.setOnClickListener(
                view12 -> {
                    if (sharedPreferences.getBoolean("showMatureContent", true)) {
                        switchShowMatureContent.setChecked(false);
                        editor.putBoolean("showMatureContent", false).apply();
                    } else {
                        switchShowMatureContent.setChecked(true);
                        editor.putBoolean("showMatureContent", true).apply();
                    }
                }
        );

        textViewProfileUsername.setText(authManager.getAuthenticatedUser().getEmail());
    }

    private void setupLogoutHandler() {
        textViewSettingsLogout.setOnClickListener(v -> {
            authManager.logout();

            Navigation.findNavController(requireView())
                    .navigate(R.id.loginFragment);
        });
    }
}
