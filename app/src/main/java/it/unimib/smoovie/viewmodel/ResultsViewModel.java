package it.unimib.smoovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;

public class ResultsViewModel extends BaseViewModel {

    private final MoviesRepository moviesRepository;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> searchResultMovieList;

    private Disposable disposableSearchResultMovieList;

    public ResultsViewModel(Application application) {
        super(application);
        searchResultMovieList = new MutableLiveData<>();
        moviesRepository = MoviesRepository.getInstance(application);
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMoviesByQuery(String query, int page) {
        disposableSearchResultMovieList = moviesRepository.getMoviesByQuery(query, page, getCurrentLocale())
                .subscribe(searchResultMovieList::postValue);

        return searchResultMovieList;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMoviesByCategory(MovieGenre category, int page) {
        disposableSearchResultMovieList = moviesRepository.getMoviesByCategory(category, page, getCurrentLocale())
                .subscribe(searchResultMovieList::postValue);

        return searchResultMovieList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if(disposableSearchResultMovieList != null && !disposableSearchResultMovieList.isDisposed())
            disposableSearchResultMovieList.dispose();
    }
}
