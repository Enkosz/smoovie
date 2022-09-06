package it.unimib.smoovie.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.utils.Constants;

public class MovieListRecyclerViewAdapter extends AbstractNotifiableListRecyclerViewAdapter<MovieModelCompact> {

    private Context context;
    private final Integer navigateId;

    public MovieListRecyclerViewAdapter(Context context, Integer navigateId) {
        this.context = context;
        this.navigateId = navigateId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        context = parent.getContext();

        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        MovieModelCompact model = items.get(position);

        this.loadImage(movieViewHolder, model);
    }

    private void loadImage(MovieViewHolder holder, MovieModelCompact model) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(holder.getImageViewMovieIcon().getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(Color.RED);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(Constants.API_POSTER_URL + model.posterPath)
                .placeholder(circularProgressDrawable)
                .into(holder.getImageViewMovieIcon());

        holder.getImageViewMovieIcon().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY, model.id);

            Navigation.findNavController(v)
                    .navigate(navigateId, bundle, new NavOptions.Builder()
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build());
        });
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewMovieIcon;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMovieIcon = itemView.findViewById(R.id.imageView_movie_icon);
        }

        public ImageView getImageViewMovieIcon() {
            return imageViewMovieIcon;
        }
    }
}
