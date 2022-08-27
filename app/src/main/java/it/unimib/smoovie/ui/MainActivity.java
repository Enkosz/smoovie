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


        bottomNavigationView.setOnItemReselectedListener(item -> {
            if(item.getItemId() == R.id.searchFragment) {
                if (navController.getCurrentDestination().getId() == R.id.movieDetailFragment){
                    while(navController.getCurrentDestination().getId() == R.id.movieDetailFragment){
                        navController.popBackStack();
                    }
                }else
                navController.popBackStack();
            }

        });


        navController.addOnDestinationChangedListener((ignored, navDestination, bundle) -> {
            // Hide the bottom nav bar when we are in the login fragment
            if (navDestination.getId() == R.id.loginFragment)
                bottomNavigationView.setVisibility(View.VISIBLE);
            else if (bottomNavigationView.getVisibility() != View.VISIBLE)
                bottomNavigationView.setVisibility(View.VISIBLE);

            if (navDestination.getId() == R.id.settingsFragment)
                bottomNavigationView.getMenu().findItem(R.id.settingsFragment).setChecked(true);
            if (navDestination.getId() == R.id.searchFragment || navDestination.getId() == R.id.resultsFragment || navDestination.getId() == R.id.movieDetailFragment)
                bottomNavigationView.getMenu().findItem(R.id.searchFragment).setChecked(true);
            if (navDestination.getId() == R.id.homeFragment || navDestination.getId() == R.id.loginFragment || navDestination.getId() == R.id.registerFragment)
                bottomNavigationView.getMenu().findItem(R.id.homeFragment).setChecked(true);


        });




    }
}
