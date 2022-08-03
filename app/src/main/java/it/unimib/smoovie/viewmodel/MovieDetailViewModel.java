package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.model.MovieDetailModel;
import it.unimib.smoovie.repository.MoviesRepository;

public class MovieDetailViewModel extends ViewModel {

    private final MutableLiveData<MovieDetailModel> movieDetail;
    private final MoviesRepository moviesRepository = MoviesRepository.getInstance();

    private Disposable disposableMovieDetail;

    public MovieDetailViewModel() {
        movieDetail = new MutableLiveData<>();
    }

    public LiveData<MovieDetailModel> getMovieDetailById(Long id) {
        disposableMovieDetail = moviesRepository.getMovieById(id)
                .subscribe(movieDetail::postValue);

        return movieDetail;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposableMovieDetail.dispose();
    }
}
