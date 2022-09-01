package it.unimib.smoovie.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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

    public LiveData<FavoriteMovie> getFavoriteMovieById(Long id) {
        System.out.println(" VIEW " + id);
        disposableFavoriteMovieList = moviesRepository.getFavoriteMovieById(id)
                .subscribe(favoriteMovie1 -> {
                    Log.println(Log.INFO, "viewmodelmovie", "MV: "+ favoriteMovie1.getFilmTitle());
                    favoriteMovie.postValue(favoriteMovie1);
                }, Throwable::printStackTrace, () -> {
                    favoriteMovie.postValue(null);
                    System.out.println(" PIPO ");
                });

        return favoriteMovie;
    }

    public Completable addFavoriteMovie(Long movieId, String userId, String filmTitle, String filmPosterPath) {
        return moviesRepository.addFavoriteMovie(movieId, userId, filmTitle, filmPosterPath);
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
