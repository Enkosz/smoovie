package it.unimib.smoovie.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.unimib.smoovie.R;
import it.unimib.smoovie.viewmodel.MovieRecyclerViewAdapter;
import it.unimib.smoovie.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView_movies);

        this.setupUI();
        this.setupViewModels();
    }

    private void setupUI() {
    }

    private void setupViewModels() {
        MovieViewModel model = new ViewModelProvider(this)
                .get(MovieViewModel.class);

        model.getMovies()
                .observe(this, movieApiModels -> {
                    logger.log(Level.INFO, String.format("Received popular movies of size %s", movieApiModels.size()));

                    MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(movieApiModels);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                });
    }
}