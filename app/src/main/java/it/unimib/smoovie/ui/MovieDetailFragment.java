package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieListRecyclerViewAdapter;
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.utils.ProgressDisplay;
import it.unimib.smoovie.viewmodel.MovieDetailViewModel;

public class MovieDetailFragment extends Fragment implements ProgressDisplay {

    private ImageView imageViewMovieDetailBackgroundPoster;
    private TextView textViewMovieDetailTitle;
    private TextView textViewMovieReleaseDate;
    private TextView textViewMovieRuntime;
    private TextView textViewMovieOverview;
    private RecyclerView recyclerViewMovieSuggestions;
    private ImageButton imageButtonBackNavigation;
    private ImageButton buttonMovieDetailAddFavorite;

    private LinearLayout movieDetailContainer;
    private ConstraintLayout loadingContainer;
    private MovieDetailViewModel movieDetailViewModel;

    private boolean isFavorite = false;

    private Disposable addFavoriteMovieDisposable;
    private Disposable deleteFavoriteMovieDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        imageViewMovieDetailBackgroundPoster = view.findViewById(R.id.imageView_movieDetail_backgroundPoster);
        textViewMovieDetailTitle = view.findViewById(R.id.textView_movieDetail_title);
        textViewMovieReleaseDate = view.findViewById(R.id.textView_movieDetail_releaseDate);
        textViewMovieRuntime = view.findViewById(R.id.textView_movieDetail_runtime);
        textViewMovieOverview = view.findViewById(R.id.textView_movieDetail_overview);
        recyclerViewMovieSuggestions = view.findViewById(R.id.recyclerView_movieDetail_suggestions);
        imageButtonBackNavigation = view.findViewById(R.id.imageButton_movieDetail_back);
        buttonMovieDetailAddFavorite = view.findViewById(R.id.button_movieDetail_addFavorite);

        movieDetailContainer = view.findViewById(R.id.movie_detail_container);
        loadingContainer = view.findViewById(R.id.movie_detail_loadingContainer);

        showProgress();
        setupUI();
        return view;
    }

    private void setupUI() {
        movieDetailViewModel = new ViewModelProvider(requireActivity()).get(MovieDetailViewModel.class);

        setupMovieDetailView();
        setupMovieDetailRecommendedView();
        setupMovieDetailFavoriteView();
    }

    private void setupMovieDetailFavoriteView() {
        Long id = requireArguments().getLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY);

        movieDetailViewModel.getFavoriteMovieById(id)
                .observe(getViewLifecycleOwner(), favoriteMovie -> buttonMovieDetailAddFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star_16)));

        buttonMovieDetailAddFavorite.setOnClickListener(v -> {
            if (isFavorite) {
                deleteFavoriteMovieDisposable = movieDetailViewModel.deleteFavoriteMovie(id)
                        .subscribe(() -> {
                            buttonMovieDetailAddFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star_16));
                            isFavorite = false;
                        });
            } else {
                addFavoriteMovieDisposable = movieDetailViewModel.addFavoriteMovie(id, "123")
                        .subscribe(() -> {
                            buttonMovieDetailAddFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_star_16_fill));
                            isFavorite = true;
                        });
            }
        });
    }

    private void setupMovieDetailView() {
        Long id = requireArguments().getLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY);

        movieDetailViewModel.getMovieDetailById(id)
                .observe(getViewLifecycleOwner(), modelExtendedResponseWrapper -> {
                    if (modelExtendedResponseWrapper.hasErrors()) {
                        Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_LONG).show();

                        Navigation.findNavController(requireView())
                                .navigate(R.id.homeFragment, new Bundle(), new NavOptions.Builder()
                                        .setExitAnim(android.R.anim.fade_out)
                                        .setPopEnterAnim(android.R.anim.fade_in)
                                        .build());
                        return;
                    }

                    MovieModelExtended movieModelExtended = modelExtendedResponseWrapper.getResponse();

                    textViewMovieDetailTitle.setText(movieModelExtended.title);
                    textViewMovieReleaseDate.setText(movieModelExtended.releaseDate);
                    textViewMovieOverview.setText(movieModelExtended.overview);
                    textViewMovieRuntime.setText(getString(R.string.movie_detail_runtime, movieModelExtended.getRuntimeHours(), movieModelExtended.getRuntimeMinutes()));

                    Glide.with(requireContext())
                            .load(Constants.API_POSTER_URL + movieModelExtended.backdropPath)
                            .into(imageViewMovieDetailBackgroundPoster);

                    hideProgress();
                });
    }

    private void setupMovieDetailRecommendedView() {
        Long id = requireArguments().getLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(getContext());
        recyclerViewMovieSuggestions.setLayoutManager(layoutManager);
        recyclerViewMovieSuggestions.setAdapter(adapter);

        imageButtonBackNavigation.setOnClickListener(v -> Navigation.findNavController(v)
                .popBackStack());

        EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                movieDetailViewModel.getMovieDetailSuggestionsById(id, current_page);
            }
        };

        recyclerViewMovieSuggestions.addOnScrollListener(scrollListener);
        movieDetailViewModel.getMovieDetailSuggestionsById(id, 1)
                .observe(getViewLifecycleOwner(), responseWrapper -> {
                    if (responseWrapper.hasErrors()) {
                        Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_LONG).show();

                        Navigation.findNavController(requireView())
                                .navigate(R.id.homeFragment, new Bundle(), new NavOptions.Builder()
                                        .setExitAnim(android.R.anim.fade_out)
                                        .setPopEnterAnim(android.R.anim.fade_in)
                                        .build());
                        return;
                    }

                    adapter.addItems(responseWrapper.getResponse().movies);
                });
    }

    @Override
    public void showProgress() {
        loadingContainer.setVisibility(View.VISIBLE);
        movieDetailContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingContainer.setVisibility(View.GONE);
        movieDetailContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        if (addFavoriteMovieDisposable != null && !addFavoriteMovieDisposable.isDisposed())
            addFavoriteMovieDisposable.dispose();
        if (deleteFavoriteMovieDisposable != null && !deleteFavoriteMovieDisposable.isDisposed())
            deleteFavoriteMovieDisposable.dispose();

        super.onDestroyView();
    }
}
