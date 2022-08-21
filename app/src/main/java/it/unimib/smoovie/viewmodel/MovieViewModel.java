package it.unimib.smoovie.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieViewModel extends ViewModel {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> popularMovies;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> topRatedMovies;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> nowPlayingMovies;

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

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getPopularMovies(int page) {
            disposablePopularMovies = moviesRepository.getPopularMovies(page)
                    .subscribe(popularMovies::postValue);

        return popularMovies;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getTopRatedMovies(int page) {
            disposableTopRatedMovies = moviesRepository.getTopRatedMovies(page)
                    .subscribe(topRatedMovies::postValue);

        return topRatedMovies;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getNowPlayingMovies(int page) {
            disposableNowPlayingMovies = moviesRepository.getNowPlayingMovies(page)
                    .subscribe(nowPlayingMovies::postValue);

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
