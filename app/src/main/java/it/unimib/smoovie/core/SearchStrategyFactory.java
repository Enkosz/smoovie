package it.unimib.smoovie.core;

import android.content.Context;
import android.os.Bundle;

import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class SearchStrategyFactory {

    private final ResultsViewModel viewModel;

    public SearchStrategyFactory(final ResultsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public SearchStrategy getStrategyByArguments(Bundle bundle, final Context context) {
        if(bundle.getString(Constants.SEARCH_MOVIE_TITLE_BUNDLE_KEY) != null)
            return new TitleSearchStrategy(viewModel, bundle.getString(Constants.SEARCH_MOVIE_TITLE_BUNDLE_KEY));
        else
            return new GenreSearchStrategy(viewModel, MovieGenre.valueOf(bundle.getString(Constants.SEARCH_MOVIE_GENRE_BUNDLE_KEY)), context);
    }
}
