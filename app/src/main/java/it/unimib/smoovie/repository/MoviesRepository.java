package it.unimib.smoovie.repository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.api.service.MovieApiService;
import it.unimib.smoovie.api.service.ServiceProvider;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModel;

public class MoviesRepository {

    private static MoviesRepository instance;
    private final MovieApiService movieApiService;

    private MoviesRepository() {
        this.movieApiService = ServiceProvider.getInstance().getMovieApiService();
    }

    public static MoviesRepository getInstance() {
        if(instance == null) {
            instance = new MoviesRepository();
        }

        return instance;
    }

    public Observable<ApiResponse<MovieModel>> getPopularMovies() {
        return movieApiService.fetchPopularMovies("cf29714667ee6f5b118a61f362a58378")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ApiResponse<MovieModel>> getTopRatedMovies() {
        return movieApiService.fetchTopRatedMovies("cf29714667ee6f5b118a61f362a58378")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ApiResponse<MovieModel>> getNowPlayingMovies() {
        return movieApiService.fetchNowPlayingMovies("cf29714667ee6f5b118a61f362a58378")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}