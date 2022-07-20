package it.unimib.smoovie.ui;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.MovieModel;

public interface SearchStrategy {

    LiveData<List<MovieModel>> search(int page);
}
