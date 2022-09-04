package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    navController.navigate(R.id.homeFragment);
                    break;
                case R.id.searchFragment:
                    navController.navigate(R.id.searchFragment);
                    break;
                case R.id.settingsFragment:
                    navController.navigate(R.id.settingsFragment);
                    break;
            }
        });

        navController.addOnDestinationChangedListener((ignored, navDestination, bundle) -> {
            if(navDestination.getId() == R.id.loginFragment || navDestination.getId() == R.id.registerFragment)
                bottomNavigationView.setVisibility(View.GONE);
            else if(bottomNavigationView.getVisibility() != View.VISIBLE)
                bottomNavigationView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
        //return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.bottom_navigation_view), dra)
    }
}
