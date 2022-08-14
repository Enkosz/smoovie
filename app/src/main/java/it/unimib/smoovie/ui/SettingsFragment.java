package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SwitchCompat switchShowMatureContent = view.findViewById(R.id.switch_show_mature_content);
        switchShowMatureContent.setChecked(sharedPreferences.getBoolean("showMatureContent", false));
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

        SwitchCompat switchStreamUsingCellular = view.findViewById(R.id.switch_stream_using_cellular);
        switchStreamUsingCellular.setChecked(sharedPreferences.getBoolean("streamUsingCellular", false));
        switchStreamUsingCellular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("streamUsingCellular", true);
                    Log.i("stream_using_cellular", "true");
                } else {
                    editor.putBoolean("streamUsingCellular", false);
                    Log.i("stream_using_cellular", "false");
                }
                editor.apply();
            }
        });
        return view;
    }
}
