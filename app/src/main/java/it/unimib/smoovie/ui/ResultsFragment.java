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
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class ResultsFragment extends Fragment {

    private ResultsViewModel viewModel;
    private MovieSearchResultRecyclerVewAdapter adapter;
    private TextView textViewSearchQuery;
    private ImageButton backButton;
    private RecyclerView recyclerView;

    private SearchStrategyFactory searchStrategyFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_results);
        backButton = view.findViewById(R.id.button_search_result_back);
        textViewSearchQuery = view.findViewById(R.id.textView_search_results);

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
        SearchStrategy searchStrategy = searchStrategyFactory.getStrategyByArguments(getArguments());

        EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                searchStrategy.search(current_page);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        backButton.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.searchFragment));

        // Fetch the first page of data
        searchStrategy.search(1)
                .observe(getViewLifecycleOwner(), movieModels -> adapter.addItems(movieModels));
    }
}
