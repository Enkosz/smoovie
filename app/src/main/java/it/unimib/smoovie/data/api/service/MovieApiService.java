package it.unimib.smoovie.data.api.service;

import java.util.List;

import it.unimib.smoovie.data.model.ApiResponse;
import it.unimib.smoovie.data.model.MovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("users/{user}/repos")
    Call<List<MovieModel>> fetchMovies();

    @GET("movie/{id}")
    Call<MovieModel> fetchMovie(@Path("id") Long id, @Query("api_key") String key);

    @GET("movie/popular")
    Call<ApiResponse<MovieModel>> fetchPopularMovies(@Query("api_key") String key);
}
