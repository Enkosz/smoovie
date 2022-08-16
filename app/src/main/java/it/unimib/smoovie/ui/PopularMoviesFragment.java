package it.unimib.smoovie.ui;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;

public class PopularMoviesFragment extends FeedMoviesFragment {

    public PopularMoviesFragment() {
        super(R.layout.fragment_popular_movies, R.id.recyclerView_nowPlayingMovies, R.id.progressBar_popularMovies);
    }

    @Override
    protected LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> fetchMovies(int page) {
        return this.getViewModel().getPopularMovies(page);
    }
}
