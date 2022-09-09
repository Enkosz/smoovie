package it.unimib.smoovie.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.logging.Logger;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieViewModel extends BaseViewModel {

    private final MoviesRepository moviesRepository;

    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> popularMovies;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> topRatedMovies;
    private final MutableLiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> nowPlayingMovies;

    private Disposable disposablePopularMovies;
    private Disposable disposableTopRatedMovies;
    private Disposable disposableNowPlayingMovies;

    public MovieViewModel(Application application) {
        super(application);

        popularMovies = new MutableLiveData<>();
        topRatedMovies = new MutableLiveData<>();
        nowPlayingMovies = new MutableLiveData<>();

        moviesRepository = MoviesRepository.getInstance(application);
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getPopularMovies(int page) {
            disposablePopularMovies = moviesRepository.getPopularMovies(page, getCurrentLocale(), isAdultPreference())
                    .subscribe(popularMovies::postValue);

        return popularMovies;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getTopRatedMovies(int page) {
            disposableTopRatedMovies = moviesRepository.getTopRatedMovies(page, getCurrentLocale(), isAdultPreference())
                    .subscribe(topRatedMovies::postValue);

        return topRatedMovies;
    }

    public LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> getNowPlayingMovies(int page) {
            disposableNowPlayingMovies = moviesRepository.getNowPlayingMovies(page, getCurrentLocale(), isAdultPreference())
                    .subscribe(nowPlayingMovies::postValue);

        return nowPlayingMovies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if(disposablePopularMovies != null && !disposablePopularMovies.isDisposed())
            disposablePopularMovies.dispose();

        if(disposableTopRatedMovies != null && !disposableTopRatedMovies.isDisposed())
            disposableTopRatedMovies.dispose();

        if(disposableNowPlayingMovies != null && !disposableNowPlayingMovies.isDisposed())
            disposableNowPlayingMovies.dispose();
    }
}
