package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.smoovie.adapter.MovieListRecyclerViewAdapter;
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.viewmodel.MovieViewModel;

public abstract class FeedMoviesFragment extends Fragment {

    private ViewModelProvider modelProvider;

    private final Integer fragmentId;
    private final Integer recyclerViewId;


    public FeedMoviesFragment(Integer fragmentId, Integer recyclerViewId) {
        this.fragmentId = fragmentId;
        this.recyclerViewId = recyclerViewId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragmentId, container, false);
        RecyclerView mRecyclerViewCountryNews = view.findViewById(recyclerViewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(getContext());

        mRecyclerViewCountryNews.setAdapter(adapter);
        mRecyclerViewCountryNews.setLayoutManager(layoutManager);

        EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                fetchMovies(current_page);
            }
        };

        mRecyclerViewCountryNews.addOnScrollListener(scrollListener);
        fetchMovies(1)
            .observe(getViewLifecycleOwner(), adapter::addItems);

        return view;
    }

    protected MovieViewModel getViewModel() {
        if(modelProvider == null)
            modelProvider = new ViewModelProvider(this);

        return modelProvider.get(MovieViewModel.class);
    }

    protected abstract LiveData<List<MovieModelCompact>> fetchMovies(int page);
}
