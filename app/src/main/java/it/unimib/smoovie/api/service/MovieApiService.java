package it.unimib.smoovie.api.service;


import io.reactivex.Single;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/{id}")
    Single<MovieModelExtended> fetchMovie(@Path("id") Long id, @Query("api_key") String key, @Query("language") String language);

    @GET("movie/popular")
    Single<ApiResponse<MovieModelCompact>> fetchPopularMovies(@Query("api_key") String key, @Query("page") Integer page, @Query("language") String language);

    @GET("movie/now_playing")
    Single<ApiResponse<MovieModelCompact>> fetchNowPlayingMovies(@Query("api_key") String key, @Query("page") Integer page, @Query("language") String language);

    @GET("movie/top_rated")
    Single<ApiResponse<MovieModelCompact>> fetchTopRatedMovies(@Query("api_key") String key, @Query("page") Integer page, @Query("language") String language);

    @GET("search/movie")
    Single<ApiResponse<MovieModelCompact>> fetchMoviesByQuery(@Query("api_key") String key, @Query("query") String query, @Query("page") Integer page, @Query("language") String language);

    @GET("discover/movie")
    Single<ApiResponse<MovieModelCompact>> fetchMoviesByGenre(@Query("api_key") String key, @Query("with_genres") Integer code, @Query("page") Integer page, @Query("language") String language);

    @GET("movie/{id}/similar")
    Single<ApiResponse<MovieModelCompact>> fetchSimilarMovies(@Path("id") Long id, @Query("api_key") String key, @Query("page") Integer page, @Query("language") String language);
}
