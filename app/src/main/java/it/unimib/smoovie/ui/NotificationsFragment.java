package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import it.unimib.smoovie.R;

public class NotificationsFragment extends Fragment {
    private ImageButton backButton;
    private RelativeLayout relativeLayoutBackButton;
    private RelativeLayout relativeLayoutGeneralUpdates;
    private RelativeLayout relativeLayoutContentUpdates;
    private RelativeLayout relativeLayoutPromotionalUpdates;
    private SwitchCompat switchGeneralUpdates;
    private SwitchCompat switchContentUpdates;
    private SwitchCompat switchPromotionalUpdates;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        backButton = view.findViewById(R.id.imageButton_notificationsSettings_back);
        relativeLayoutBackButton = view.findViewById(R.id.relative_layout_notifications_back_button);
        relativeLayoutGeneralUpdates = view.findViewById(R.id.relative_layout_general_updates);
        relativeLayoutContentUpdates = view.findViewById(R.id.relative_layout_content_updates);
        relativeLayoutPromotionalUpdates = view.findViewById(R.id.relative_layout_promotional_updates);
        switchGeneralUpdates = view.findViewById(R.id.switch_general_updates);
        switchContentUpdates = view.findViewById(R.id.switch_content_updates);
        switchPromotionalUpdates = view.findViewById(R.id.switch_promotional_updates);
        sharedPreferences = requireContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setupUI();
        return view;
    }

    private void setupUI() {
        backButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        relativeLayoutBackButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        switchGeneralUpdates.setChecked(sharedPreferences.getBoolean("generalUpdates", true));
        switchGeneralUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> editor.putBoolean("generalUpdates", isChecked).apply());
        relativeLayoutGeneralUpdates.setOnClickListener(
                view1 -> {
                    if (sharedPreferences.getBoolean("generalUpdates", true)) {
                        switchGeneralUpdates.setChecked(false);
                        editor.putBoolean("generalUpdates", false).apply();
                    } else {
                        switchGeneralUpdates.setChecked(true);
                        editor.putBoolean("generalUpdates", true).apply();
                    }
                });

        switchContentUpdates.setChecked(sharedPreferences.getBoolean("contentUpdates", true));
        switchContentUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> editor.putBoolean("contentUpdates", isChecked).apply());
        relativeLayoutContentUpdates.setOnClickListener(
                view2 -> {
                    if (sharedPreferences.getBoolean("contentUpdates", true)) {
                        switchContentUpdates.setChecked(false);
                        editor.putBoolean("contentUpdates", false).apply();
                    } else {
                        switchContentUpdates.setChecked(true);
                        editor.putBoolean("contentUpdates", true).apply();
                    }
                });

        switchPromotionalUpdates.setChecked(sharedPreferences.getBoolean("promotionalUpdates", true));
        switchPromotionalUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> editor.putBoolean("promotionalUpdates", isChecked).apply());
        relativeLayoutPromotionalUpdates.setOnClickListener(
                view3 -> {
                    if (sharedPreferences.getBoolean("promotionalUpdates", true)) {
                        switchPromotionalUpdates.setChecked(false);
                        editor.putBoolean("promotionalUpdates", false).apply();
                    } else {
                        switchPromotionalUpdates.setChecked(true);
                        editor.putBoolean("promotionalUpdates", true).apply();
                    }
                });
    }
}
