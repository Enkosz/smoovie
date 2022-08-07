package it.unimib.smoovie.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieViewModel extends ViewModel {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final MutableLiveData<List<MovieModelCompact>> popularMovies;
    private final MutableLiveData<List<MovieModelCompact>> topRatedMovies;
    private final MutableLiveData<List<MovieModelCompact>> nowPlayingMovies;

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

    public LiveData<List<MovieModelCompact>> getPopularMovies(int page) {
            disposablePopularMovies = moviesRepository.getPopularMovies(page)
                    .subscribe(movieModelApiResponse -> popularMovies.postValue(movieModelApiResponse.movies));

        return popularMovies;
    }

    public LiveData<List<MovieModelCompact>> getTopRatedMovies(int page) {
            disposableTopRatedMovies = moviesRepository.getTopRatedMovies(page)
                    .subscribe(movieModelApiResponse -> topRatedMovies.postValue(movieModelApiResponse.movies));

        return topRatedMovies;
    }

    public LiveData<List<MovieModelCompact>> getNowPlayingMovies(int page) {
            disposableNowPlayingMovies = moviesRepository.getNowPlayingMovies(page)
                    .subscribe(movieModelApiResponse -> nowPlayingMovies.postValue(movieModelApiResponse.movies));

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
