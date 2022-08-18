package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Locale;
import java.util.Objects;

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
        Log.i("langcheck", "en: "+englishChecked);
        Log.i("langcheck", "it: "+italianChecked);

        if (englishChecked) {
            radioLanguageEnglish.setChecked(true);
        }
        if (italianChecked) {
            radioLanguageItalian.setChecked(true);
        }

        RadioGroup radioGroupLanguage = view.findViewById(R.id.radiogroup_language);
        radioGroupLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String tag = "radio_language";
                switch (checkedId) {
                    case R.id.radio_language_english:
                        Log.i(tag, "onCheckedChanged: english");
                        changeLocale("en");
                        break;
                    case R.id.radio_language_italian:
                        Log.i(tag, "onCheckedChanged: italian");
                        changeLocale("it");
                        break;
                    default:
                        break;
                }
                editor.putBoolean("english", radioLanguageEnglish.isChecked());
                editor.putBoolean("italian", radioLanguageItalian.isChecked());
                editor.apply();
                requireActivity().recreate();
            }
        });

        return view;

    }

    public void changeLocale(String lang) {
        Log.i("lang", "changeLanguage: " + lang);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getActivity().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        Log.i("lang2", "ciao willy!");
    }


}