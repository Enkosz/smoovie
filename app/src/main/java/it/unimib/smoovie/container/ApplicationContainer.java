package it.unimib.smoovie.container;

import it.unimib.smoovie.data.repository.MoviesRepository;

public class ApplicationContainer {

    private static ApplicationContainer instance;
    private MoviesRepository moviesRepository;

    public static ApplicationContainer getInstance() {
        if(instance == null) {
            synchronized (ApplicationContainer.class) {
                instance = new ApplicationContainer();
            }
        }

        return instance;
}

    public MoviesRepository getMoviesRepository() {
        if(moviesRepository == null) {
            moviesRepository = MoviesRepository.getInstance();
        }

        return moviesRepository;
    }
}
