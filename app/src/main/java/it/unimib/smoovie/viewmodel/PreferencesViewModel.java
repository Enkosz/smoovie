package it.unimib.smoovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;
import it.unimib.smoovie.room.model.FavoriteMovie;

public class PreferencesViewModel extends AndroidViewModel {
    private final MutableLiveData<ResponseWrapper<MovieModelExtended>> movieDetail;
    private final MutableLiveData<List<FavoriteMovie>> favoriteMovies;

    private final MoviesRepository moviesRepository;

    private Disposable disposableFavoriteMovieList;
    private Disposable disposableMovieDetail;

    public PreferencesViewModel(Application application) {
        super(application);
        favoriteMovies = new MutableLiveData<>();
        movieDetail = new MutableLiveData<>();

        moviesRepository = MoviesRepository.getInstance(application);
    }

    public LiveData<List<FavoriteMovie>> getAllFavouriteMovies() {
        disposableFavoriteMovieList = moviesRepository.getAllFavourites()
                .subscribe(favoriteMovies::postValue);

        return favoriteMovies;
    }

    public LiveData<ResponseWrapper<MovieModelExtended>> getMovieDetailById(Long id) {
        disposableMovieDetail = moviesRepository.getMovieById(id)
                .subscribe(movieDetail::postValue);

        return movieDetail;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposableFavoriteMovieList.dispose();
    }
}
