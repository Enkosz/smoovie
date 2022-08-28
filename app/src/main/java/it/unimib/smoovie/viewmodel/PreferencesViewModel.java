package it.unimib.smoovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.repository.MoviesRepository;
import it.unimib.smoovie.room.model.FavoriteMovie;

public class PreferencesViewModel extends AndroidViewModel {
    private final MutableLiveData<List<FavoriteMovie>> favoriteMovies;

    private final MoviesRepository moviesRepository;

    private Disposable disposableFavoriteMovieList;

    public PreferencesViewModel(Application application) {
        super(application);
        favoriteMovies = new MutableLiveData<>();

        moviesRepository = MoviesRepository.getInstance(application);
    }

    public LiveData<List<FavoriteMovie>> getAllFavouriteMovies() {
        disposableFavoriteMovieList = moviesRepository.getAllFavourites()
                .subscribe(favoriteMovies::postValue);

        return favoriteMovies;
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        disposableFavoriteMovieList.dispose();
    }
}
