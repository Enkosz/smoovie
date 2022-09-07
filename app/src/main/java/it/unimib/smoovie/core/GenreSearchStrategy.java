package it.unimib.smoovie.core;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class GenreSearchStrategy implements SearchStrategy {

    private final ResultsViewModel viewModel;
    private final MovieGenre category;
    private final Context context;

    public GenreSearchStrategy(final ResultsViewModel viewModel, final MovieGenre category, final Context context) {
        this.viewModel = viewModel;
        this.category = category;
        this.context = context;
    }

    @Override
    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> search(int page) {
        Log.i("movie", "" + category);
        return viewModel.getMoviesByCategory(category, page);
    }

    @Override
    public String getQuery() {
        int genreIdentifier = context.getResources()
                .getIdentifier(String.format("movie_genre_%s", category.getCategory().toLowerCase()), "string", context.getPackageName());

        return context.getString(genreIdentifier);
    }

}
