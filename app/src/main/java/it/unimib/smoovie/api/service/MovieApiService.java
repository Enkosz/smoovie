package it.unimib.smoovie.api.service;


import io.reactivex.Observable;
import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/{id}")
    Observable<MovieModel> fetchMovie(@Path("id") Long id, @Query("api_key") String key);

    @GET("movie/popular")
    Observable<ApiResponse<MovieModel>> fetchPopularMovies(@Query("api_key") String key);

    @GET("movie/now_playing")
    Observable<ApiResponse<MovieModel>> fetchNowPlayingMovies(@Query("api_key") String key);

    @GET("movie/top_rated")
    Observable<ApiResponse<MovieModel>> fetchTopRatedMovies(@Query("api_key") String key);
}
