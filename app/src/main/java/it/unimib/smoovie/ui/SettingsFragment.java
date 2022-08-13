package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import it.unimib.smoovie.R;

public class SettingsFragment extends Fragment {

    private SwitchCompat switchMature;
    private SwitchCompat switchStreamCellular;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switchMature = view.findViewById(R.id.mature_content);
        switchMature.setChecked(sharedPreferences.getBoolean("showMature", false));
        switchMature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("showMature", true);
                    Log.i("mature", "true");
                } else {
                    editor.putBoolean("showMature", false);
                    Log.i("mature", "false");
                }
                editor.apply();
            }
        });

        switchStreamCellular = view.findViewById(R.id.stream_cellular);
        switchStreamCellular.setChecked(sharedPreferences.getBoolean("streamCellular", false));
        switchStreamCellular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("streamCellular", true);
                    Log.i("cellular", "true");
                } else {
                    editor.putBoolean("streamCellular", false);
                    Log.i("cellular", "false");
                }
                editor.apply();
            }
        });
        return view;
    }
}
