package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieDetailViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<MovieModelExtended>> movieDetail;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> movieDetailSuggestionList;
    private final MoviesRepository moviesRepository = MoviesRepository.getInstance();

    private Disposable disposableMovieDetail;
    private Disposable disposableMovieDetailSuggestion;

    public MovieDetailViewModel() {
        movieDetail = new MutableLiveData<>();
        movieDetailSuggestionList = new MutableLiveData<>();
    }

    public LiveData<ResponseWrapper<MovieModelExtended>> getMovieDetailById(Long id) {
        disposableMovieDetail = moviesRepository.getMovieById(id)
                .subscribe(movieDetail::postValue);

        return movieDetail;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMovieDetailSuggestionsById(Long movieId, int page) {
        disposableMovieDetailSuggestion = moviesRepository.getSimilarMoviesOfMovie(movieId, page)
                .subscribe(movieDetailSuggestionList::postValue);

        return movieDetailSuggestionList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposableMovieDetail.dispose();
        disposableMovieDetailSuggestion.dispose();
    }
}
