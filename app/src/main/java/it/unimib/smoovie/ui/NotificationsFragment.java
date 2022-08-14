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
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import it.unimib.smoovie.R;

public class NotificationsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ImageButton backButton = view.findViewById(R.id.imageButton_notificationsSettings_back);
        backButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();

        SwitchCompat switchGeneralUpdates = view.findViewById(R.id.switch_general_updates);
        switchGeneralUpdates.setChecked(sharedPreferences.getBoolean("generalUpdates", true));
        switchGeneralUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("generalUpdates", true);
                } else {
                    editor.putBoolean("generalUpdates", false);
                }
                editor.apply();
            }
        });
        RelativeLayout relativeLayoutGeneralUpdates = view.findViewById(R.id.relative_layout_general_updates);
        relativeLayoutGeneralUpdates.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sharedPreferences.getBoolean("generalUpdates", true)) {
                            switchGeneralUpdates.setChecked(false);
                            editor.putBoolean("generalUpdates", false).apply();
                        } else {
                            switchGeneralUpdates.setChecked(true);
                            editor.putBoolean("generalUpdates", true).apply();
                        }
                    }
                });

        SwitchCompat switchContentUpdates = view.findViewById(R.id.switch_content_updates);
        switchContentUpdates.setChecked(sharedPreferences.getBoolean("contentUpdates", true));
        switchContentUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("contentUpdates", true);
                } else {
                    editor.putBoolean("contentUpdates", false);
                }
                editor.apply();
            }
        });
        RelativeLayout relativeLayoutContentUpdates = view.findViewById(R.id.relative_layout_content_updates);
        relativeLayoutContentUpdates.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sharedPreferences.getBoolean("contentUpdates", true)) {
                            switchContentUpdates.setChecked(false);
                            editor.putBoolean("contentUpdates", false).apply();
                        } else {
                            switchContentUpdates.setChecked(true);
                            editor.putBoolean("contentUpdates", true).apply();
                        }
                    }
                });

        SwitchCompat switchPromotionalUpdates = view.findViewById(R.id.switch_promotional_updates);
        switchPromotionalUpdates.setChecked(sharedPreferences.getBoolean("promotionalUpdates", true));
        switchPromotionalUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("promotionalUpdates", true);
                } else {
                    editor.putBoolean("promotionalUpdates", false);
                }
                editor.apply();
            }
        });
        RelativeLayout relativeLayoutPromotionalUpdates = view.findViewById(R.id.relative_layout_promotional_updates);
        relativeLayoutPromotionalUpdates.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sharedPreferences.getBoolean("promotionalUpdates", true)) {
                            switchPromotionalUpdates.setChecked(false);
                            editor.putBoolean("promotionalUpdates", false).apply();
                        } else {
                            switchPromotionalUpdates.setChecked(true);
                            editor.putBoolean("promotionalUpdates", true).apply();
                        }
                    }
                });

        return view;
    }
}
