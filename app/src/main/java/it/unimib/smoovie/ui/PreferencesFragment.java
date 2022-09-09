package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.UserPreferencesRecyclerViewAdapter;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.utils.ProgressDisplay;
import it.unimib.smoovie.viewmodel.PreferencesViewModel;

public class PreferencesFragment extends Fragment implements ProgressDisplay {

    private PreferencesViewModel viewModel;
    private UserPreferencesRecyclerViewAdapter adapter;
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noPreferencesTextView;

    private HashSet<MovieModelExtended> preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_preferences);
        backButton = view.findViewById(R.id.button_search_preferences_back);
        progressBar = view.findViewById(R.id.progressBar_preferences);
        noPreferencesTextView = view.findViewById(R.id.no_preferences_textview);
        preferences = new HashSet<>();

        showProgress();
        setupViewModel();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PreferencesViewModel.class);
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
                    if (!currentList.isEmpty()) {
                        noPreferencesTextView.setVisibility(View.GONE);
                        adapter.clear();
                        hideProgress();
                        adapter.addItems(currentList);
                    } else {
                        hideProgress();
                    }
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
