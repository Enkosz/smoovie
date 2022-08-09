package it.unimib.smoovie.core;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.MovieModelCompact;

public interface SearchStrategy {

    LiveData<List<MovieModelCompact>> search(int page);

    String getQuery();
}
