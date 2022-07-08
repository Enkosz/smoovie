package it.unimib.smoovie.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Logger;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieListRecyclerViewAdapter;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPopularMovies;
    private RecyclerView recyclerViewTopRatedMovies;
    private RecyclerView recyclerViewNowPlayingMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupUI();
        this.setupViewModels();
    }

    private void setupUI() {
        recyclerViewPopularMovies = findViewById(R.id.recyclerView_popular);
        recyclerViewTopRatedMovies = findViewById(R.id.recyclerView_topRated);
        recyclerViewNowPlayingMovies = findViewById(R.id.recyclerView_nowPlaying);
    }

    private void setupViewModels() {
        MovieViewModel model = new ViewModelProvider(this)
                .get(MovieViewModel.class);

        model.getPopularMovies()
                .observe(this, movieModels -> createMovieRecyclerView(movieModels, recyclerViewPopularMovies));
        model.getTopRatedMovies()
                .observe(this, movieModels -> createMovieRecyclerView(movieModels, recyclerViewTopRatedMovies));
        model.getNowPlayingMovies()
                .observe(this, movieModels -> createMovieRecyclerView(movieModels, recyclerViewNowPlayingMovies));
    }

    private void createMovieRecyclerView(List<MovieModel> movieModels, RecyclerView recyclerView) {
        MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(movieModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}