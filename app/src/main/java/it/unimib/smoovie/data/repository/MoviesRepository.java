package it.unimib.smoovie.data.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Function;

import it.unimib.smoovie.data.api.service.MovieApiService;
import it.unimib.smoovie.data.api.service.ServiceProvider;
import it.unimib.smoovie.data.model.ApiResponse;
import it.unimib.smoovie.data.model.MovieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository instance;

    private final MovieApiService movieApiService;
    private final MutableLiveData<List<MovieModel>> movieApiModelMutableLiveData;
    private final MutableLiveData<MovieModel> selectedMovieMutableLiveData;

    private MoviesRepository() {
        this.movieApiService = ServiceProvider.getInstance().getMovieApiService();
        this.movieApiModelMutableLiveData = new MutableLiveData<>();
        this.selectedMovieMutableLiveData = new MutableLiveData<>();
    }

    public static MoviesRepository getInstance() {
        if(instance == null) {
            instance = new MoviesRepository();
        }

        return instance;
    }

    public MutableLiveData<List<MovieModel>> getMovies() {
        Call<ApiResponse<MovieModel>> call = movieApiService.fetchPopularMovies("cf29714667ee6f5b118a61f362a58378");
        call.enqueue(new DefaultCallback<>(movieApiModelApiResponse -> {
            movieApiModelMutableLiveData.postValue(movieApiModelApiResponse.movies);
            return null;
        }));

        return movieApiModelMutableLiveData;
    }

    public MutableLiveData<MovieModel> getMovie(Long id) {
        Call<MovieModel> call = movieApiService.fetchMovie(id, "cf29714667ee6f5b118a61f362a58378");

        call.enqueue(new Callback<MovieModel>() {

            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                selectedMovieMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

        return selectedMovieMutableLiveData;
    }

    private static class DefaultCallback<T> implements Callback<T> {

        private final Function<T, Void> fnCompleted;

        public DefaultCallback(Function<T, Void> fnCompleted) {
            this.fnCompleted = fnCompleted;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            T responseBody = response.body();

            fnCompleted.apply(responseBody);
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {

        }
    }
}