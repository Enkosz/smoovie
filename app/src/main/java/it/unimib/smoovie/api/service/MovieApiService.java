package it.unimib.smoovie.api.service;


import io.reactivex.Single;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieDetailModel;
import it.unimib.smoovie.model.MovieModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/{id}")
    Single<MovieDetailModel> fetchMovie(@Path("id") Long id, @Query("api_key") String key);

    @GET("movie/popular")
    Single<ApiResponse<MovieModel>> fetchPopularMovies(@Query("api_key") String key, @Query("page") Integer page);

    @GET("movie/now_playing")
    Single<ApiResponse<MovieModel>> fetchNowPlayingMovies(@Query("api_key") String key, @Query("page") Integer page);

    @GET("movie/top_rated")
    Single<ApiResponse<MovieModel>> fetchTopRatedMovies(@Query("api_key") String key, @Query("page") Integer page);

    @GET("search/movie")
    Single<ApiResponse<MovieModel>> fetchMoviesByQuery(@Query("api_key") String key, @Query("query") String query, @Query("page") Integer page);

    @GET("discover/movie")
    Single<ApiResponse<MovieModel>> fetchMoviesByGenre(@Query("api_key") String key, @Query("with_genres") Integer code, @Query("page") Integer page);
}
