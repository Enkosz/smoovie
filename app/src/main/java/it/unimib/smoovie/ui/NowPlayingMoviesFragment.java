package it.unimib.smoovie.ui;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModel;

public class NowPlayingMoviesFragment extends FeedMoviesFragment {

    public NowPlayingMoviesFragment() {
        super(R.layout.fragment_up_coming_movies, R.id.recyclerView_nowPlayingMovies);
    }

    @Override
    protected LiveData<List<MovieModel>> fetchMovies() {
        return this.getViewModel().getNowPlayingMovies();
    }
}
