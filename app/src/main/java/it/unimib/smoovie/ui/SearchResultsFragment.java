package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
import it.unimib.smoovie.model.MovieCategory;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.viewmodel.SearchResultsViewModel;

public class SearchResultsFragment extends Fragment {

    private SearchResultsViewModel viewModel;
    private MovieSearchResultRecyclerVewAdapter movieListRecyclerViewAdapter;
    private TextView textViewSearchQuery;
    private ImageButton backButton;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_results);
        backButton = view.findViewById(R.id.button_search_result_back);
        textViewSearchQuery = view.findViewById(R.id.textView_search_results);

        setupViewModel();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchResultsViewModel.class);
    }

    private void setupUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        assert getArguments() != null;
        String query = getArguments().getString(Constants.SEARCH_MOVIE_TITLE_BUNDLE_KEY);

        if(query != null) {
            textViewSearchQuery.setText("Risultati per " + query);

            viewModel.getMoviesByQuery(query)
                    .observe(getViewLifecycleOwner(), movieModels -> {
                        movieListRecyclerViewAdapter = new MovieSearchResultRecyclerVewAdapter(movieModels, getContext());
                        recyclerView.setAdapter(movieListRecyclerViewAdapter);
                    });
        } else {
            MovieCategory category = MovieCategory.valueOf(getArguments().getString(Constants.SEARCH_MOVIE_CATEGORY_BUNDLE_KEY));
            textViewSearchQuery.setText("Risultati per " + category.getCategory());

            viewModel.getMoviesByCategory(category)
                    .observe(getViewLifecycleOwner(), movieModels -> {
                        movieListRecyclerViewAdapter = new MovieSearchResultRecyclerVewAdapter(movieModels, getContext());
                        recyclerView.setAdapter(movieListRecyclerViewAdapter);
                    });
        }

        backButton.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.searchFragment));
    }
}
