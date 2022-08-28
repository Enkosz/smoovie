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
import it.unimib.smoovie.adapter.UserPreferencesRecyclerViewAdapter;
import it.unimib.smoovie.core.SearchStrategy;
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_preferences);
        backButton = view.findViewById(R.id.button_search_preferences_back);
        progressBar = view.findViewById(R.id.progressBar_preferences);

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


        /*EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                searchStrategy.search(current_page);
            }
        };*/

        //recyclerView.addOnScrollListener(scrollListener);
        backButton.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        // Fetch the first page of data
        /*searchStrategy.search(1)
                .observe(getViewLifecycleOwner(), responseWrapper -> {
                    if(responseWrapper.hasErrors()) {
                        Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_LONG).show();

                        Navigation.findNavController(requireView())
                                .navigate(R.id.searchFragment, new Bundle(), new NavOptions.Builder()
                                        .setExitAnim(android.R.anim.fade_out)
                                        .setPopEnterAnim(android.R.anim.fade_in)
                                        .build());
                        return;
                    }

                    adapter.addItems(responseWrapper.getResponse().movies);
                    hideProgress();
                });*/

        viewModel.getAllFavouriteMovies()
                .observe(getViewLifecycleOwner(), currentList -> {
                    Log.println(Log.INFO, "preferences-fragment", currentList.toString());
                    //adapter.addItems(currentList);
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
