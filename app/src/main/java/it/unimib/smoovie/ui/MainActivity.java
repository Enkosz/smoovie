package it.unimib.smoovie.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import it.unimib.smoovie.R;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Logic to manage the behavior of the BottomNavigationView and Toolbar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        SwitchCompat switch_mature = findViewById(R.id.mature_content);

        if (switch_mature != null) {
            switch_mature.setOnCheckedChangeListener(this);
            Log.i("sucapipo", "set!");
        }
//    switch_mature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////        @Override
////        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////            Log.v("Switch state", ""+isChecked);
////        }
//    });

//        SharedPreferences settings = getSharedPreferences("CoolPreferences", 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("show_mature_content", "true");
//        editor.commit();
//
//        String silent = settings.getString("show_mature_content", "value");
//        Log.i("sucapipo", silent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.v("Switch state", "" + isChecked);
        Log.i("sucapipo", "" + isChecked);
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
    }
}