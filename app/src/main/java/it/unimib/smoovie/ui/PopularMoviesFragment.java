package it.unimib.smoovie.ui;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModelCompact;

public class PopularMoviesFragment extends FeedMoviesFragment {

    public PopularMoviesFragment() {
        super(R.layout.fragment_popular_movies, R.id.recyclerView_nowPlayingMovies);
    }

    @Override
    protected LiveData<List<MovieModelCompact>> fetchMovies(int page) {
        return this.getViewModel().getPopularMovies(page);
    }
}
