package it.unimib.smoovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import it.unimib.smoovie.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        setupNavControllerAuthenticationFilter();
    }

    private void setupNavControllerAuthenticationFilter() {

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((ignored, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.loginFragment)
                bottomNavigationView.setVisibility(View.GONE);
            else if (bottomNavigationView.getVisibility() != View.VISIBLE)
                bottomNavigationView.setVisibility(View.VISIBLE);

            Log.i("test", "setupNavControllerAuthenticationFilter: --------------------------------");
        });




    }
}
