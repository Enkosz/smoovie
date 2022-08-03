package it.unimib.smoovie.core;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.MovieCategory;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class CategorySearchStrategy implements SearchStrategy {

    private final ResultsViewModel viewModel;
    private final MovieCategory category;

    public CategorySearchStrategy(final ResultsViewModel viewModel, final MovieCategory category) {
        this.viewModel = viewModel;
        this.category = category;
    }

    @Override
    public LiveData<List<MovieModel>> search(int page) {
        return viewModel.getMoviesByCategory(category, page);
    }
}
