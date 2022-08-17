package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean englishChecked = sharedPreferences.getBoolean("english",false);
        boolean italianChecked = sharedPreferences.getBoolean("italian",false);
        if(englishChecked) {
            radioLanguageEnglish.setChecked(true);
        } else if (italianChecked) {
            radioLanguageItalian.setChecked(true);
        }

        RadioGroup radioGroupLanguage = view.findViewById(R.id.radiogroup_language);
        radioGroupLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String tag = "radio_language";
                switch (checkedId) {
                    case R.id.radio_language_english:
//                        radioGroupLanguage.check(R.id.radio_language_english);
                        Log.i(tag, "onCheckedChanged: english");
                        break;
                    case R.id.radio_language_italian:
//                        radioGroupLanguage.check(R.id.radio_language_italian);
                        Log.i(tag, "onCheckedChanged: italian");
                        break;
                    default:
                        break;
                }
                editor.putBoolean("english", radioLanguageEnglish.isChecked());
                editor.putBoolean("italian", radioLanguageItalian.isChecked());
                editor.apply();
            }
        });
        return view;
    }

}