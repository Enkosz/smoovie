package it.unimib.smoovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;
import it.unimib.smoovie.room.model.FavoriteMovie;

public class MovieDetailViewModel extends AndroidViewModel {

    private final MutableLiveData<ResponseWrapper<MovieModelExtended>> movieDetail;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> movieDetailSuggestionList;
    private final MutableLiveData<FavoriteMovie> favoriteMovie;

    private final MoviesRepository moviesRepository;

    private Disposable disposableMovieDetail;
    private Disposable disposableMovieDetailSuggestion;
    private Disposable disposableFavoriteMovieList;

    public MovieDetailViewModel(Application application) {
        super(application);
        movieDetail = new MutableLiveData<>();
        movieDetailSuggestionList = new MutableLiveData<>();
        favoriteMovie = new MutableLiveData<>();

        moviesRepository = MoviesRepository.getInstance(application);
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

    public LiveData<FavoriteMovie> getFavoriteMovieById(Long id, Long userId) {
        disposableFavoriteMovieList = moviesRepository.getFavoriteMovieById(id, userId)
                .subscribe(favoriteMovie::postValue);

        return favoriteMovie;
    }

    public Completable addFavoriteMovie(Long movieId, String userId) {
        return moviesRepository.addFavoriteMovie(movieId, userId);
    }
    public Completable deleteFavoriteMovie(Long movieId) {
        return moviesRepository.deleteFavoriteMovie(movieId);
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        disposableMovieDetail.dispose();
        disposableMovieDetailSuggestion.dispose();
        disposableFavoriteMovieList.dispose();
    }
}
