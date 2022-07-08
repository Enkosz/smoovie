package it.unimib.smoovie.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.model.Category;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieViewModel extends ViewModel {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final MutableLiveData<List<MovieModel>> popularMovies;
    private final MutableLiveData<List<MovieModel>> topRatedMovies;
    private final MutableLiveData<List<MovieModel>> nowPlayingMovies;

    private Disposable disposablePopularMovies;
    private Disposable disposableTopRatedMovies;
    private Disposable disposableNowPlayingMovies;

    public MovieViewModel() {
        popularMovies = new MutableLiveData<>();
        topRatedMovies = new MutableLiveData<>();
        nowPlayingMovies = new MutableLiveData<>();
    }

    private final MoviesRepository moviesRepository = ApplicationContainer.getInstance()
            .getMoviesRepository();

    public LiveData<List<MovieModel>> getPopularMovies() {
        if (popularMovies.getValue() == null) {
            disposablePopularMovies = moviesRepository.getPopularMovies()
                    .subscribe(movieModelApiResponse -> {
                        popularMovies.postValue(movieModelApiResponse.movies);
                    });
        }

        return popularMovies;
    }

    public LiveData<List<MovieModel>> getTopRatedMovies() {
        if (topRatedMovies.getValue() == null) {
            disposableTopRatedMovies = moviesRepository.getTopRatedMovies()
                    .subscribe(movieModelApiResponse -> {
                        topRatedMovies.postValue(movieModelApiResponse.movies);
                    });
        }

        return topRatedMovies;
    }

    public LiveData<List<MovieModel>> getNowPlayingMovies() {
        if (nowPlayingMovies.getValue() == null) {
            disposableTopRatedMovies = moviesRepository.getNowPlayingMovies()
                    .subscribe(movieModelApiResponse -> {
                        nowPlayingMovies.postValue(movieModelApiResponse.movies);
                    });
        }

        return nowPlayingMovies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposablePopularMovies.dispose();
        disposableTopRatedMovies.dispose();
        disposableNowPlayingMovies.dispose();
    }
}
