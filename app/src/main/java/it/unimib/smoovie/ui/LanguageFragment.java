package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
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

    private RadioButton radioLanguageEnglish;
    private RadioButton radioLanguageItalian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        radioLanguageEnglish = view.findViewById(R.id.radio_language_english);
        radioLanguageItalian = view.findViewById(R.id.radio_language_italian);

        ImageButton backButton = view.findViewById(R.id.imageButton_languageSettings_back);
        backButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        RelativeLayout relativeLayoutBackButton = view.findViewById(R.id.relative_layout_language_back_button);
        relativeLayoutBackButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean englishChecked = sharedPreferences.getBoolean("english", false);
        boolean italianChecked = sharedPreferences.getBoolean("italian", false);

        if (italianChecked) {
            radioLanguageItalian.setChecked(true);
            Log.i("lang", "start: checked italian");
            setLocale("it");
            editor.putBoolean("italian", radioLanguageItalian.isChecked()).apply();
        }
        else {
            radioLanguageEnglish.setChecked(true);
            Log.i("lang", "start: checked english");
            setLocale("en");
            editor.putBoolean("english", radioLanguageEnglish.isChecked()).apply();
        }

        RadioGroup radioGroupLanguage = view.findViewById(R.id.radiogroup_language);
        radioGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_language_english) {
                setLocale("en");
            } else if (checkedId == R.id.radio_language_italian) {
                setLocale("it");
            }

            editor.putBoolean("english", radioLanguageEnglish.isChecked());
            editor.putBoolean("italian", radioLanguageItalian.isChecked());
            editor.apply();
            requireActivity().recreate();
        });

        return view;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = requireActivity().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        Locale.setDefault(myLocale);
        onConfigurationChanged(conf);
    }

    public String getLanguagePref() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        boolean englishChecked = sharedPreferences.getBoolean("english", false);
        boolean italianChecked = sharedPreferences.getBoolean("italian", false);
        if (englishChecked) return "en";
        if (italianChecked) return "it";
        else return null;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        setLocale(getLanguagePref());
        super.onResume();
    }
}