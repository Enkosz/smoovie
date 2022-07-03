package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.smoovie.container.ApplicationContainer;
import it.unimib.smoovie.data.model.MovieApiModel;
import it.unimib.smoovie.data.repository.MoviesRepository;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<MovieApiModel>> movies;

    private final MoviesRepository moviesRepository = ApplicationContainer.getInstance()
            .getMoviesRepository();

    public LiveData<List<MovieApiModel>> getMovies() {
        if(movies == null) {
            movies = new MutableLiveData<>();
            movies.setValue(moviesRepository.getMovies());
        }

        return movies;
    }
}
