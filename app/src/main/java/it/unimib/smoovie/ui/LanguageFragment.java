package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Locale;

import it.unimib.smoovie.R;

public class LanguageFragment extends Fragment {
    private ImageButton backButton;
    private RadioButton radioLanguageEnglish;
    private RadioButton radioLanguageItalian;
    private RadioGroup radioGroupLanguage;
    private RelativeLayout relativeLayoutBackButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        backButton = view.findViewById(R.id.imageButton_languageSettings_back);
        radioLanguageEnglish = view.findViewById(R.id.radio_language_english);
        radioLanguageItalian = view.findViewById(R.id.radio_language_italian);
        radioGroupLanguage = view.findViewById(R.id.radiogroup_language);
        relativeLayoutBackButton = view.findViewById(R.id.relative_layout_language_back_button);
        sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setupUI();
        return view;
    }

    private void setupUI() {
        boolean englishChecked = sharedPreferences.getBoolean("english", false);
        boolean italianChecked = sharedPreferences.getBoolean("italian", false);
        if (englishChecked) radioLanguageEnglish.setChecked(true);
        if (italianChecked) radioLanguageItalian.setChecked(true);

        backButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        relativeLayoutBackButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        radioGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_language_english) changeLocale("en");
            else if (checkedId == R.id.radio_language_italian) changeLocale("it");
            editor.putBoolean("english", radioLanguageEnglish.isChecked());
            editor.putBoolean("italian", radioLanguageItalian.isChecked());
            editor.apply();
            requireActivity().recreate();
        });
    }

    public void changeLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = requireActivity().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}