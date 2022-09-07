package it.unimib.smoovie.repository;

import android.app.Application;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.api.service.MovieApiService;
import it.unimib.smoovie.api.service.ServiceProvider;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.Error;
import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import it.unimib.smoovie.room.SmoovieDatabase;
import it.unimib.smoovie.room.model.FavoriteMovie;
import it.unimib.smoovie.utils.Constants;
import retrofit2.http.Query;

public class MoviesRepository {

    private static MoviesRepository instance;

    private final MovieApiService movieApiService;
    private final SmoovieDatabase smoovieDatabase;

    private MoviesRepository(Application application) {
        this.movieApiService = ServiceProvider.getInstance().getMovieApiService();
        this.smoovieDatabase = SmoovieDatabase.getInstance(application);
    }

    public static MoviesRepository getInstance(Application application) {
        if(instance == null)
            instance = new MoviesRepository(application);

        return instance;
    }

    public Single<ResponseWrapper<MovieModelExtended>> getMovieById(Long id, String language) {
        return movieApiService.fetchMovie(id, Constants.API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Single<ResponseWrapper<ApiResponse<MovieModelCompact>>> getPopularMovies(int page, String language, Boolean includeAdult) {
        return movieApiService.fetchPopularMovies(Constants.API_KEY, page, language, includeAdult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Single<ResponseWrapper<ApiResponse<MovieModelCompact>>> getTopRatedMovies(int page, String language, Boolean includeAdult) {
        return movieApiService.fetchTopRatedMovies(Constants.API_KEY, page, language, includeAdult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Single<ResponseWrapper<ApiResponse<MovieModelCompact>>> getNowPlayingMovies(int page, String language, Boolean includeAdult) {
        return movieApiService.fetchNowPlayingMovies(Constants.API_KEY, page, language, includeAdult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Single<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMoviesByQuery(String query, int page, String language, Boolean includeAdult) {
        return movieApiService.fetchMoviesByQuery(Constants.API_KEY, query, page, language, includeAdult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Single<ResponseWrapper<ApiResponse<MovieModelCompact>>> getMoviesByCategory(MovieGenre category, int page, String language, Boolean includeAdult) {
        return movieApiService.fetchMoviesByGenre(Constants.API_KEY, category.getCode(), page, language, includeAdult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Single<ResponseWrapper<ApiResponse<MovieModelCompact>>> getSimilarMoviesOfMovie(Long movieId, int page, String language, Boolean includeAdult) {
        return movieApiService.fetchSimilarMovies(movieId, Constants.API_KEY, page, language, includeAdult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseWrapper::new)
                .onErrorReturn(throwable -> new ResponseWrapper<>(Collections.singletonList(new Error("change me"))));
    }

    public Maybe<FavoriteMovie> getFavoriteMovieById(Long id, String userId) {
        return smoovieDatabase.favoriteMovieDao()
                .getFavoriteMovieById(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<List<FavoriteMovie>> getAllFavourites() {
        return smoovieDatabase.favoriteMovieDao()
                .getAllFavoriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteFavoriteMovie(Long movieId) {
        return smoovieDatabase.favoriteMovieDao()
                .deleteFavoriteMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable addFavoriteMovie(Long movieId, String userId, String filmTitle, String filmPosterPath) {
        return smoovieDatabase.favoriteMovieDao()
                .insertAll(new FavoriteMovie(userId, movieId, filmTitle, filmPosterPath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}