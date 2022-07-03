package it.unimib.smoovie.data.datasource;

import java.util.List;

import it.unimib.smoovie.data.api.MovieApi;
import it.unimib.smoovie.data.model.MovieApiModel;

public class MovieRemoteDataSource implements MovieDataSource {

    private final MovieApi movieApi;

    private static MovieRemoteDataSource instance;

    private MovieRemoteDataSource(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public static MovieRemoteDataSource getInstance(MovieApi movieApi) {
        if(instance == null) {
            instance = new MovieRemoteDataSource(movieApi);
        }

        return instance;
    }

    @Override
    public List<MovieApiModel> getMovies() {
        return movieApi.fetchMovies();
    }
}
