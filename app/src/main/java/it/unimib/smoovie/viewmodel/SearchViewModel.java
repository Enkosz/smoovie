package it.unimib.smoovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import it.unimib.smoovie.model.MovieCategory;

public class SearchViewModel extends ViewModel {

    private final LiveData<List<MovieCategory>> movieCategoryList;

    public SearchViewModel() {
        movieCategoryList = new MutableLiveData<>(Arrays.asList(MovieCategory.values()));
    }

    public LiveData<List<MovieCategory>> getMovieCategoryList() {
        return movieCategoryList;
    }
}
