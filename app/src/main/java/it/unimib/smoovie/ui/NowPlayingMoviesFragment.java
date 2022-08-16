package it.unimib.smoovie.ui;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;

public class NowPlayingMoviesFragment extends FeedMoviesFragment {

    public NowPlayingMoviesFragment() {
        super(R.layout.fragment_up_now_playing_movies, R.id.recyclerView_nowPlayingMovies, R.id.progressBar_nowPlayingMovies);
    }

    @Override
    protected LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> fetchMovies(int page) {
        return this.getViewModel().getNowPlayingMovies(page);
    }
}
