package it.unimib.smoovie.core;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.core.SearchStrategy;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class TitleSearchStrategy implements SearchStrategy {

    private final ResultsViewModel viewModel;
    private final String query;

    public TitleSearchStrategy(final ResultsViewModel viewModel, final String query) {
        this.viewModel = viewModel;
        this.query = query;
    }

    @Override
    public LiveData<List<MovieModel>> search(int page) {
       return viewModel.getMoviesByQuery(query, page);
    }
}
