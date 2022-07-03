package it.unimib.smoovie.data.api;

import java.util.List;

import it.unimib.smoovie.data.model.MovieApiModel;

public interface MovieApi {

    List<MovieApiModel> fetchMovies();
}
