package it.unimib.smoovie.repository;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.api.service.MovieApiService;
import it.unimib.smoovie.api.service.ServiceProvider;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieCategory;
import it.unimib.smoovie.model.MovieDetailModel;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.utils.Constants;

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

    public Single<MovieDetailModel> getMovieById(Long id) {
        return movieApiService.fetchMovie(id, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModel>> getPopularMovies() {
        return movieApiService.fetchPopularMovies(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModel>> getTopRatedMovies() {
        return movieApiService.fetchTopRatedMovies(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModel>> getNowPlayingMovies() {
        return movieApiService.fetchNowPlayingMovies(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModel>> getMoviesByQuery(String query, int page) {
        return movieApiService.fetchMoviesByQuery(Constants.API_KEY, query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModel>> getMoviesByCategory(MovieCategory category, int page) {
        return movieApiService.fetchMoviesByGenre(Constants.API_KEY, category.getCode(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}