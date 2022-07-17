package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
import it.unimib.smoovie.viewmodel.SearchResultsViewModel;

public class SearchResultsFragment extends Fragment {

    private SearchResultsViewModel viewModel;
    private MovieSearchResultRecyclerVewAdapter movieListRecyclerViewAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_results);

        setupViewModel();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchResultsViewModel.class);
    }

    private void setupUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        viewModel.getMoviesByQuery()
                .observe(getViewLifecycleOwner(), movieModels -> {
                    movieListRecyclerViewAdapter = new MovieSearchResultRecyclerVewAdapter(movieModels, getContext());
                    recyclerView.setAdapter(movieListRecyclerViewAdapter);
                });
    }
}
