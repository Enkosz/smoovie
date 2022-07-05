package it.unimib.smoovie.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.logging.Logger;

import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.data.model.MovieModel;
import it.unimib.smoovie.data.repository.MoviesRepository;

public class MovieViewModel extends ViewModel {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private MutableLiveData<List<MovieModel>> movies;
    private MutableLiveData<MovieModel> selectedMovie;

    private final MoviesRepository moviesRepository = ApplicationContainer.getInstance()
            .getMoviesRepository();

    public LiveData<List<MovieModel>> getMovies() {
        if(movies == null) {
            movies = new MutableLiveData<>();
            movies  = moviesRepository.getMovies();
        }

        return movies;
    }

    public LiveData<MovieModel> getMovie(Long id) {
        if(selectedMovie == null) {
            selectedMovie = new MutableLiveData<>();
            selectedMovie = moviesRepository.getMovie(id);
        }

        return selectedMovie;
    }
}
