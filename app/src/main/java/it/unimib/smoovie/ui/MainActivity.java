package it.unimib.smoovie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import it.unimib.smoovie.R;
import it.unimib.smoovie.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        setupNavControllerAuthenticationFilter();
        setupLanguageFromPreferences();
        Log.i("SDK", "Startup completed, SDK level: " + android.os.Build.VERSION.SDK_INT);
    }

    private void setupLanguageFromPreferences() {
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String preferenceLocale = sharedPreferences.getString(Constants.SHARED_PREFERENCE_LANGUAGE, Constants.LANGUAGE_ENGLISH);

        String currentLocale = getBaseContext()
                .getResources()
                .getConfiguration()
                .getLocales()
                .toLanguageTags()
                .toUpperCase(Locale.ROOT);
        Log.i(TAG, String.format("Setting up language from preferences, current preference locale is %s, current locale is %s", preferenceLocale, currentLocale));

        // change the locale if is different from the shared preferences
        if(!currentLocale.equals(preferenceLocale)) {
            Locale locale = new Locale(preferenceLocale);
            Locale.setDefault(locale);
            Resources resources = getBaseContext().getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config,null);
            this.recreate();
        }
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
