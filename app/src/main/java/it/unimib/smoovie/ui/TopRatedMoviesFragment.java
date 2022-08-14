package it.unimib.smoovie.ui;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModelCompact;

public class TopRatedMoviesFragment extends FeedMoviesFragment {

    public TopRatedMoviesFragment() {
        super(R.layout.fragment_top_rated_movies, R.id.recyclerView_topRatedMovies, R.id.progressBar_nowPlayingMovies);
    }

    @Override
    protected LiveData<List<MovieModelCompact>> fetchMovies(int page) {
        return this.getViewModel().getTopRatedMovies(page);
    }
}
