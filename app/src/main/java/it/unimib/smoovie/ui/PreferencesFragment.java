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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.UserPreferencesRecyclerViewAdapter;
import it.unimib.smoovie.core.SearchStrategy;
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.utils.ProgressDisplay;
import it.unimib.smoovie.viewmodel.MovieViewModel;
import it.unimib.smoovie.viewmodel.PreferencesViewModel;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class PreferencesFragment extends Fragment implements ProgressDisplay {

    private PreferencesViewModel viewModel;
    private UserPreferencesRecyclerViewAdapter adapter;
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private List<MovieModelExtended> preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_preferences);
        backButton = view.findViewById(R.id.button_search_preferences_back);
        progressBar = view.findViewById(R.id.progressBar_preferences);
        preferences = new ArrayList<>();

        showProgress();
        setupViewModel();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(PreferencesViewModel.class);
    }

    private void setupUI() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new UserPreferencesRecyclerViewAdapter(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        viewModel.getAllFavouriteMovies()
                .observe(getViewLifecycleOwner(), currentList -> {
                    currentList.forEach(favoriteMovie -> {
                        Log.println(Log.INFO, "preferences-fragment", "" + favoriteMovie.getFilmId());
                        viewModel.getMovieDetailById(favoriteMovie.getFilmId()).observe(getViewLifecycleOwner(), movieModelExtendedResponseWrapper -> {
                            Log.println(Log.INFO, "preferences-fragment", "" + movieModelExtendedResponseWrapper.getResponse().title);
                            preferences.add(movieModelExtendedResponseWrapper.getResponse());
                        });
                    });
                    hideProgress();
                    adapter.addItems(preferences);
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
