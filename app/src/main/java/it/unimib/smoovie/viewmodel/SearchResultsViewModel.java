package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.repository.MoviesRepository;

public class SearchResultsViewModel extends ViewModel {

    private final MutableLiveData<List<MovieModel>> searchResultMovieList;

    private Disposable disposableSearchResultMovieList;

    public SearchResultsViewModel() {
        searchResultMovieList = new MutableLiveData<>();
    }

    private final MoviesRepository moviesRepository = ApplicationContainer.getInstance()
            .getMoviesRepository();

    public LiveData<List<MovieModel>> getMoviesByQuery() {
        if (searchResultMovieList.getValue() == null) {
            disposableSearchResultMovieList = moviesRepository.getPopularMovies()
                    .subscribe(movieModelApiResponse -> {
                        searchResultMovieList.postValue(movieModelApiResponse.movies);
                    });
        }

        return searchResultMovieList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposableSearchResultMovieList.dispose();
    }
}
