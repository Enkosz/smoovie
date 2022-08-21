package it.unimib.smoovie.core;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class TitleSearchStrategy implements SearchStrategy {

    private final ResultsViewModel viewModel;
    private final String query;

    public TitleSearchStrategy(final ResultsViewModel viewModel, final String query) {
        this.viewModel = viewModel;
        this.query = query;
    }

    @Override
    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> search(int page) {
       return viewModel.getMoviesByQuery(query, page);
    }

    @Override
    public String getQuery() {
        return query;
    }
}
