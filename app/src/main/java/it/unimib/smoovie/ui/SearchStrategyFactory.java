package it.unimib.smoovie.ui;

import android.os.Bundle;

import it.unimib.smoovie.model.MovieCategory;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class SearchStrategyFactory {

    private final ResultsViewModel viewModel;

    public SearchStrategyFactory(final ResultsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public SearchStrategy getStrategyByArguments(Bundle bundle) {
        if(bundle.getString(Constants.SEARCH_MOVIE_TITLE_BUNDLE_KEY) != null)
            return new TitleSearchStrategy(viewModel, bundle.getString(Constants.SEARCH_MOVIE_TITLE_BUNDLE_KEY));
        else
            return new CategorySearchStrategy(viewModel, MovieCategory.valueOf(bundle.getString(Constants.SEARCH_MOVIE_CATEGORY_BUNDLE_KEY)));
    }
}
