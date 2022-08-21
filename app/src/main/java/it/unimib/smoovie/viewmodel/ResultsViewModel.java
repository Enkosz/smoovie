package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;

public class ResultsViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> searchResultMovieList;

    private Disposable disposableSearchResultMovieList;

    public ResultsViewModel() {
        searchResultMovieList = new MutableLiveData<>();
    }

    private final MoviesRepository moviesRepository = ApplicationContainer.getInstance()
            .getMoviesRepository();

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMoviesByQuery(String query, int page) {
        disposableSearchResultMovieList = moviesRepository.getMoviesByQuery(query, page)
                .subscribe(searchResultMovieList::postValue);

        return searchResultMovieList;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMoviesByCategory(MovieGenre category, int page) {
        disposableSearchResultMovieList = moviesRepository.getMoviesByCategory(category, page)
                .subscribe(searchResultMovieList::postValue);

        return searchResultMovieList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposableSearchResultMovieList.dispose();
    }
}
