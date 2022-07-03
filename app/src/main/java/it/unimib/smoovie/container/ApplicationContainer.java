package it.unimib.smoovie.container;

import it.unimib.smoovie.data.api.MovieApi;
import it.unimib.smoovie.data.api.retrofit.RetrofitMovieApi;
import it.unimib.smoovie.data.datasource.MovieDataSource;
import it.unimib.smoovie.data.datasource.MovieRemoteDataSource;
import it.unimib.smoovie.data.repository.MoviesRepository;

public class ApplicationContainer {

    private static ApplicationContainer instance;

    private MoviesRepository moviesRepository;

    public static ApplicationContainer getInstance() {
        if(instance == null) {
            instance = new ApplicationContainer();
        }

        return instance;
    }

    public MoviesRepository getMoviesRepository() {
        if(moviesRepository == null) {
            MovieApi movieApi = new RetrofitMovieApi();
            MovieDataSource movieDataSource = MovieRemoteDataSource.getInstance(movieApi);
            moviesRepository = MoviesRepository.getInstance(movieDataSource);
        }

        return moviesRepository;
    }
}
