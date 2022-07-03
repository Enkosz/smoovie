package it.unimib.smoovie.data.api.retrofit;

import java.util.ArrayList;
import java.util.List;

import it.unimib.smoovie.data.api.MovieApi;
import it.unimib.smoovie.data.model.MovieApiModel;

public class RetrofitMovieApi implements MovieApi {

    private final List<MovieApiModel> movies = new ArrayList<>();

    public RetrofitMovieApi() {
        movies.add(new MovieApiModel());
    }

    @Override
    public List<MovieApiModel> fetchMovies() {
        return movies;
    }
}
