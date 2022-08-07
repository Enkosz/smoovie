package it.unimib.smoovie.repository;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.api.service.MovieApiService;
import it.unimib.smoovie.api.service.ServiceProvider;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.model.MovieModelCompact;
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

    public Single<MovieModelExtended> getMovieById(Long id) {
        return movieApiService.fetchMovie(id, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModelCompact>> getPopularMovies(int page) {
        return movieApiService.fetchPopularMovies(Constants.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModelCompact>> getTopRatedMovies(int page) {
        return movieApiService.fetchTopRatedMovies(Constants.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModelCompact>> getNowPlayingMovies(int page) {
        return movieApiService.fetchNowPlayingMovies(Constants.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModelCompact>> getMoviesByQuery(String query, int page) {
        return movieApiService.fetchMoviesByQuery(Constants.API_KEY, query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModelCompact>> getMoviesByCategory(MovieGenre category, int page) {
        return movieApiService.fetchMoviesByGenre(Constants.API_KEY, category.getCode(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ApiResponse<MovieModelCompact>> getSimilarMoviesOfMovie(Long movieId, int page) {
        return movieApiService.fetchSimilarMovies(movieId, Constants.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}