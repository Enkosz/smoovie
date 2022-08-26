package it.unimib.smoovie.ui;

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

        AtomicBoolean toReset = new AtomicBoolean(false);
        AtomicBoolean inMovieDetail = new AtomicBoolean(false);
        AtomicBoolean test = new AtomicBoolean(false);
        // Hide the bottom nav bar when we are in the login fragment
        navController.addOnDestinationChangedListener((ignored, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.homeFragment) {
                Log.i("test", "setupNavControllerAuthenticationFilter: homeFragment");
                toReset.set(false);
            }
            if (navDestination.getId() == R.id.searchFragment) {
                Log.i("test", "setupNavControllerAuthenticationFilter: searchFragment");
                toReset.set(false);
            }
            if (navDestination.getId() == R.id.resultsFragment) {
                if (toReset.get()) {
                    Log.i("test", "setupNavControllerAuthenticationFilter: if, resetting to search");
                    Log.i("test", "setupNavControllerAuthenticationFilter: inside toReset = " + toReset.get());
                    navController.navigate(R.id.searchFragment);
                    toReset.set(false);
                } else {
                    Log.i("test", "setupNavControllerAuthenticationFilter: else, toReset = " + toReset.get());
                    Log.i("test", "setupNavControllerAuthenticationFilter: resultsFragment");
                    Log.i("test", "setupNavControllerAuthenticationFilter: setting toReset to true");
                    toReset.set(true);
                }
            }
            if (navDestination.getId() == R.id.movieDetailFragment) {
                if (!inMovieDetail.get()) {
                    toReset.set(false);
                } else {
                    inMovieDetail.set(false);
                    test.set(true);
                }
                Log.i("test", "setupNavControllerAuthenticationFilter: movieDetailFragment");
                if (toReset.get()) {
                    Log.i("test", "setupNavControllerAuthenticationFilter: if, resetting to search");
                    Log.i("test", "setupNavControllerAuthenticationFilter: inside toReset = " + toReset.get());
                    navController.navigate(R.id.searchFragment);
                    toReset.set(false);
                } else {
                    Log.i("test", "setupNavControllerAuthenticationFilter: else, toReset = " + toReset.get());
                    Log.i("test", "setupNavControllerAuthenticationFilter: movieDetailFragment");
                    Log.i("test", "setupNavControllerAuthenticationFilter: setting toReset to true");
                    toReset.set(true);
                    inMovieDetail.set(true);
                }
                Log.i("test", "setupNavControllerAuthenticationFilter: inMovieDetail = " + inMovieDetail.get());
            }
            if (navDestination.getId() == R.id.settingsFragment) {
                Log.i("test", "setupNavControllerAuthenticationFilter: settingsFragment");
                toReset.set(false);
            }

            if (navDestination.getId() == R.id.loginFragment)
                bottomNavigationView.setVisibility(View.GONE);
            else if (bottomNavigationView.getVisibility() != View.VISIBLE)
                bottomNavigationView.setVisibility(View.VISIBLE);

            Log.i("test", "setupNavControllerAuthenticationFilter: --------------------------------");
        });
    }
}
