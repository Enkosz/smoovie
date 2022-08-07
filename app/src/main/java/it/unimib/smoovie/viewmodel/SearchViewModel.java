package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import it.unimib.smoovie.model.MovieGenre;

public class SearchViewModel extends ViewModel {

    private final LiveData<List<MovieGenre>> movieCategoryList;

    public SearchViewModel() {
        movieCategoryList = new MutableLiveData<>(Arrays.asList(MovieGenre.values()));
    }

    public LiveData<List<MovieGenre>> getMovieCategoryList() {
        return movieCategoryList;
    }
}
