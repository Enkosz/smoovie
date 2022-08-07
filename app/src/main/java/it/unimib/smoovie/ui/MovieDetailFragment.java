package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieListRecyclerViewAdapter;
import it.unimib.smoovie.adapter.MovieSearchResultRecyclerVewAdapter;
import it.unimib.smoovie.core.SearchStrategy;
import it.unimib.smoovie.core.SearchStrategyFactory;
import it.unimib.smoovie.listener.EndlessRecyclerOnScrollListener;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.viewmodel.MovieDetailViewModel;
import it.unimib.smoovie.viewmodel.ResultsViewModel;

public class MovieDetailFragment extends Fragment {

    private ImageView imageViewMovieDetailBackgroundPoster;
    private TextView textViewMovieDetailTitle;
    private TextView textViewMovieReleaseDate;
    private TextView textViewMovieRuntime;
    private TextView textViewMovieOverview;
    private RecyclerView recyclerViewMovieSuggestions;

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

        setupUI();
        return view;
    }

    private void setupUI() {
        MovieDetailViewModel movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        Long id = requireArguments().getLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY);

        movieDetailViewModel.getMovieDetailById(id)
                .observe(getViewLifecycleOwner(), movieModelExtended -> {

                    textViewMovieDetailTitle.setText(movieModelExtended.title);
                    textViewMovieReleaseDate.setText(movieModelExtended.releaseDate);
                    textViewMovieRuntime.setText(String.valueOf(movieModelExtended.runtime));
                    textViewMovieOverview.setText(movieModelExtended.overview);

                    Glide.with(requireContext())
                            .load(Constants.API_POSTER_URL + movieModelExtended.backdropPath)
                            .into(imageViewMovieDetailBackgroundPoster);
                });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(getContext());
        recyclerViewMovieSuggestions.setLayoutManager(layoutManager);
        recyclerViewMovieSuggestions.setAdapter(adapter);


        EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                movieDetailViewModel.getMovieDetailSuggestionsById(id, current_page);
            }
        };

        recyclerViewMovieSuggestions.addOnScrollListener(scrollListener);
        movieDetailViewModel.getMovieDetailSuggestionsById(id, 1)
                .observe(getViewLifecycleOwner(), adapter::addItems);
    }
}
