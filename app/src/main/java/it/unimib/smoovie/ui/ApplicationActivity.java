package it.unimib.smoovie.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import it.unimib.smoovie.R;

public class ApplicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Logic to manage the behavior of the BottomNavigationView and Toolbar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

}
