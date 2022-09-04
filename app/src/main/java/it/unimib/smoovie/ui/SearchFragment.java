package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieCategoryViewAdapter;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.utils.ProgressDisplay;
import it.unimib.smoovie.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment implements ProgressDisplay {

    private RecyclerView recyclerViewSearch;
    private MovieCategoryViewAdapter viewAdapter;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerViewSearch = view.findViewById(R.id.recyclerView_search);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar_search);

        showProgress();
        setupUI();
        setupViewModel();
        return view;
    }

    private void setupUI() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext(), FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        viewAdapter = new MovieCategoryViewAdapter(requireContext());
        recyclerViewSearch.setLayoutManager(layoutManager);
        recyclerViewSearch.setAdapter(viewAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SEARCH_MOVIE_TITLE_BUNDLE_KEY, query);

                Navigation.findNavController(searchView)
                        .navigate(R.id.action_searchFragment_to_resultsFragment, bundle, new NavOptions.Builder()
                                .setExitAnim(android.R.anim.fade_out)
                                .setPopEnterAnim(android.R.anim.fade_in)
                                .build());

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setupViewModel() {
        SearchViewModel searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        searchViewModel.getMovieCategoryList()
            .observe(getViewLifecycleOwner(), movieCategories -> {
                viewAdapter.addItems(movieCategories);
                hideProgress();
            });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewSearch.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerViewSearch.setVisibility(View.VISIBLE);
    }
}
