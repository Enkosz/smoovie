package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        imageViewMovieDetail = view.findViewById(R.id.imageView_movie_detail);

        setupUI();
        return view;
    }

    private void setupUI() {
        MovieDetailViewModel movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        Long id = requireArguments().getLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY);

        movieDetailViewModel.getMovieDetailById(id)
                .observe(getViewLifecycleOwner(), movieDetailModel -> {
/*
                    CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(.getImageViewMovieIcon().getContext());
                    circularProgressDrawable.setStrokeWidth(5f);
                    circularProgressDrawable.setCenterRadius(30f);
                    circularProgressDrawable.start();
*/

                    Glide.with(requireContext())
                            .load(Constants.API_POSTER_URL + movieDetailModel.posterPath)
                            .into(imageViewMovieDetail);
                });
    }

}
