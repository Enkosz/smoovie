package it.unimib.smoovie.core;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class GenreSearchStrategy implements SearchStrategy {

    private final ResultsViewModel viewModel;
    private final MovieGenre category;

    public GenreSearchStrategy(final ResultsViewModel viewModel, final MovieGenre category) {
        this.viewModel = viewModel;
        this.category = category;
    }

    @Override
    public LiveData<List<MovieModelCompact>> search(int page) {
        return viewModel.getMoviesByCategory(category, page);
    }
}
