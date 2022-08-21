package it.unimib.smoovie.core;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.smoovie.model.ApiResponse;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.ResponseWrapper;

public interface SearchStrategy {

    LiveData<ResponseWrapper<ApiResponse<MovieModelCompact>>> search(int page);

    String getQuery();
}
