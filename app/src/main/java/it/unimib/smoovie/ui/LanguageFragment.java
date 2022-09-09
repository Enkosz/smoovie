package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Locale;
import it.unimib.smoovie.R;
import it.unimib.smoovie.utils.Constants;

public class LanguageFragment extends Fragment {

    private RadioButton radioLanguageEnglish;
    private RadioButton radioLanguageItalian;
    private ImageButton backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        radioLanguageEnglish = view.findViewById(R.id.radio_language_english);
        radioLanguageItalian = view.findViewById(R.id.radio_language_italian);
        backButton = view.findViewById(R.id.imageButton_languageSettings_back);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupUI();
    }

    private void setupUI() {
        setupBackNavigation();
        setupLanguage();
    }

    private void setupBackNavigation() {
        backButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        RelativeLayout relativeLayoutBackButton = requireView().findViewById(R.id.relative_layout_language_back_button);
        relativeLayoutBackButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());
    }

    private void setupLanguage() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String languagePreference = sharedPreferences.getString(Constants.SHARED_PREFERENCE_LANGUAGE, Constants.LANGUAGE_ENGLISH);

        if (languagePreference.equals(Constants.LANGUAGE_ITALIAN))
            radioLanguageItalian.setChecked(true);
        else
            radioLanguageEnglish.setChecked(true);

        RadioGroup radioGroupLanguage = requireView().findViewById(R.id.radiogroup_language);
        radioGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            String newLanguage;

            if (checkedId == R.id.radio_language_italian) newLanguage = Constants.LANGUAGE_ITALIAN;
            else newLanguage = Constants.LANGUAGE_ENGLISH;

            changeLocale(newLanguage);
            editor.putString(Constants.SHARED_PREFERENCE_LANGUAGE, newLanguage);
            editor.apply();
            backButton.callOnClick();
        });
    }

    private void changeLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = requireContext().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,null);
        requireActivity().recreate();
    }
}