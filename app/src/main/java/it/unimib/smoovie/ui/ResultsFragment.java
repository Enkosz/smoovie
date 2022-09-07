package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.smoovie.R;
import it.unimib.smoovie.core.SearchStrategy;
import it.unimib.smoovie.core.SearchStrategyFactory;
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
import it.unimib.smoovie.utils.ProgressDisplay;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class ResultsFragment extends Fragment implements ProgressDisplay {

    private ResultsViewModel viewModel;
    private MovieSearchResultRecyclerVewAdapter adapter;
    private TextView textViewSearchQuery;
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textViewNoResultFound;

    private SearchStrategyFactory searchStrategyFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_results);
        backButton = view.findViewById(R.id.button_search_result_back);
        textViewSearchQuery = view.findViewById(R.id.textView_search_results);
        progressBar = view.findViewById(R.id.progressBar_results);
        textViewNoResultFound = view.findViewById(R.id.textView_no_result_found);

        showProgress();
        setupViewModel();
        setupStrategyFactory();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
    }

    private void setupStrategyFactory() {
        searchStrategyFactory = new SearchStrategyFactory(viewModel);
    }

    private void setupUI() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new MovieSearchResultRecyclerVewAdapter(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if(getArguments() == null) return;
        SearchStrategy searchStrategy = searchStrategyFactory.getStrategyByArguments(getArguments(), requireContext());
        textViewSearchQuery.setText(getString(R.string.movie_search_query_result_title, searchStrategy.getQuery()));

        EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                searchStrategy.search(current_page);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        backButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        // Fetch the first page of data
        searchStrategy.search(1)
                .observe(getViewLifecycleOwner(), responseWrapper -> {
                    if(responseWrapper.hasErrors()) {
                        Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_LONG).show();

                        Navigation.findNavController(requireView())
                                .popBackStack();
                        return;
                    }
                    if (!responseWrapper.getResponse().movies.isEmpty())
                        adapter.addItems(responseWrapper.getResponse().movies);
                    hideProgress();

                });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
