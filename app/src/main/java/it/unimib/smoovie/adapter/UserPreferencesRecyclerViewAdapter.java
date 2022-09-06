package it.unimib.smoovie.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModelCompact;
import it.unimib.smoovie.model.MovieModelExtended;
import it.unimib.smoovie.room.model.FavoriteMovie;
import it.unimib.smoovie.utils.Constants;

public class UserPreferencesRecyclerViewAdapter extends AbstractNotifiableListRecyclerViewAdapter<FavoriteMovie> {
    private Context context;

    public UserPreferencesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        context = parent.getContext();

        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_movie_search_result_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        FavoriteMovie model = items.get(position);

        this.loadImage(movieViewHolder, model);
    }

    private void loadImage(MovieViewHolder holder, FavoriteMovie model) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(holder.getImageViewPoster().getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(Constants.API_POSTER_URL + model.getFilmPosterPath())
                .placeholder(circularProgressDrawable)
                .into(holder.getImageViewPoster());

        holder.getTextViewTitle().setText(model.getFilmTitle());

        holder.getCardView().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.MOVIE_DETAIL_ID_BUNDLE_KEY, model.getFilmId());

            Navigation.findNavController(v)
                    .navigate(R.id.movieDetailFragment, bundle, new NavOptions.Builder()
                            .setExitAnim(android.R.anim.fade_out)
                            .setPopEnterAnim(android.R.anim.fade_in)
                            .build());
        });
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final ImageView imageViewPoster;
        private final CardView cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_movie_title);
            imageViewPoster = itemView.findViewById(R.id.imageView_movie_poster);
            cardView = itemView.findViewById(R.id.movie_card_result);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public ImageView getImageViewPoster() {
            return imageViewPoster;
        }

        public CardView getCardView() { return cardView; }
    }
}
