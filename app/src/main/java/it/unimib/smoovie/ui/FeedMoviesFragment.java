package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public abstract class FeedMoviesFragment extends Fragment implements ProgressDisplay {

    private ViewModelProvider modelProvider;
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBar;

    private final Integer fragmentId;
    private final Integer recyclerViewId;
    private final Integer progressBarId;

    public FeedMoviesFragment(Integer fragmentId, Integer recyclerViewId, Integer progressBarId) {
        this.fragmentId = fragmentId;
        this.recyclerViewId = recyclerViewId;
        this.progressBarId = progressBarId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragmentId, container, false);
        progressBar = view.findViewById(progressBarId);
        recyclerViewMovies = view.findViewById(recyclerViewId);

        this.showProgress();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(getContext());

        recyclerViewMovies.setAdapter(adapter);
        recyclerViewMovies.setLayoutManager(layoutManager);

        EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                fetchMovies(current_page);
            }
        };

        recyclerViewMovies.addOnScrollListener(scrollListener);
        fetchMovies(1)
            .observe(getViewLifecycleOwner(), movieModelCompacts -> {
                adapter.addItems(movieModelCompacts);
                hideProgress();
            });

        return view;
    }

    protected MovieViewModel getViewModel() {
        if(modelProvider == null)
            modelProvider = new ViewModelProvider(this);

        return modelProvider.get(MovieViewModel.class);
    }

    protected abstract LiveData<List<MovieModelCompact>> fetchMovies(int page);

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewMovies.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerViewMovies.setVisibility(View.VISIBLE);
    }
}
