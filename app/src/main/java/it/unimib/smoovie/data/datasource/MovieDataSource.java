package it.unimib.smoovie.data.datasource;

import java.util.List;

import it.unimib.smoovie.data.model.MovieApiModel;

public interface MovieDataSource {

    List<MovieApiModel> getMovies();
}
