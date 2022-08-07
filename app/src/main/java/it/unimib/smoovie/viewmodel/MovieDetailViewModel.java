package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieDetailViewModel extends ViewModel {

    private final MutableLiveData<MovieModelExtended> movieDetail;
    private final MutableLiveData<List<MovieModelCompact>> movieDetailSuggestionList;
    private final MoviesRepository moviesRepository = MoviesRepository.getInstance();

    private Disposable disposableMovieDetail;
    private Disposable disposableMovieDetailSuggestion;

    public MovieDetailViewModel() {
        movieDetail = new MutableLiveData<>();
        movieDetailSuggestionList = new MutableLiveData<>();
    }

    public LiveData<MovieModelExtended> getMovieDetailById(Long id) {
        disposableMovieDetail = moviesRepository.getMovieById(id)
                .subscribe(movieDetail::postValue);

        return movieDetail;
    }

    public LiveData<List<MovieModelCompact>> getMovieDetailSuggestionsById(Long movieId, int page) {
        disposableMovieDetailSuggestion = moviesRepository.getSimilarMoviesOfMovie(movieId, page)
                .subscribe(movieModelCompactApiResponse -> movieDetailSuggestionList.postValue(movieModelCompactApiResponse.movies));

        return movieDetailSuggestionList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposableMovieDetail.dispose();
        disposableMovieDetailSuggestion.dispose();
    }
}
