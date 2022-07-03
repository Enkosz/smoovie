package it.unimib.smoovie.data.repository;

import java.util.List;

import it.unimib.smoovie.data.datasource.MovieDataSource;
import it.unimib.smoovie.data.model.MovieApiModel;

public class MoviesRepository {

    private static MoviesRepository instance;

    private final MovieDataSource dataSource;

    private MoviesRepository(MovieDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static MoviesRepository getInstance(MovieDataSource dataSource) {
        if(instance == null) {
            instance = new MoviesRepository(dataSource);
        }

        return instance;
    }

    public List<MovieApiModel> getMovies() {
        return this.dataSource.getMovies();
    }
}