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

import com.bumptech.glide.Glide;

import it.unimib.smoovie.R;
import it.unimib.smoovie.utils.Constants;
import it.unimib.smoovie.viewmodel.MovieDetailViewModel;

public class MovieDetailFragment extends Fragment {

    private ImageView imageViewMovieDetail;
    private TextView textViewMovieDetailTitle;
    private TextView textViewMovieReleaseDate;
    private TextView textViewMovieRuntime;
    private TextView textViewMovieOverview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        imageViewMovieDetail = view.findViewById(R.id.imageView_movie_detail);
        textViewMovieDetailTitle = view.findViewById(R.id.textView_movieDetail_title);
        textViewMovieReleaseDate = view.findViewById(R.id.textView_movieDetail_releaseDate);
        textViewMovieRuntime = view.findViewById(R.id.textView_movieDetail_runtime);
        textViewMovieOverview = view.findViewById(R.id.textView_movieDetail_overview);

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
                            .load(Constants.API_POSTER_URL + movieModelExtended.posterPath)
                            .into(imageViewMovieDetail);
                });
    }

}
