package it.unimib.smoovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModel;
import it.unimib.smoovie.utils.Constants;

public class MovieSearchResultRecyclerVewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<MovieModel> movieModelList;
    private final Context context;

    public MovieSearchResultRecyclerVewAdapter(List<MovieModel> movieModelList, Context context) {
        this.movieModelList = movieModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_movie_search_result_item, parent, false);

        return new MovieSearchResultRecyclerVewAdapter.MovieSearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieSearchResultRecyclerVewAdapter.MovieSearchResultViewHolder movieSearchResultViewHolder = (MovieSearchResultRecyclerVewAdapter.MovieSearchResultViewHolder) holder;
        MovieModel model = movieModelList.get(position);

        TextView textViewTitle = movieSearchResultViewHolder.getTextViewTitle();
        TextView textViewCategory = movieSearchResultViewHolder.getTextViewCategory();
        ImageView imageViewPoster = movieSearchResultViewHolder.getImageViewPoster();

        textViewTitle.setText(model.title);
        textViewCategory.setText("Category placeholder");

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(imageViewPoster.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(Constants.API_POSTER_URL + model.posterPath)
                .placeholder(circularProgressDrawable)
                .into(imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public static class MovieSearchResultViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final TextView textViewCategory;
        private final ImageView imageViewPoster;

        public MovieSearchResultViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_movie_title);
            textViewCategory = itemView.findViewById(R.id.textView_movie_category);
            imageViewPoster = itemView.findViewById(R.id.imageView_movie_poster);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewCategory() {
            return textViewCategory;
        }

        public ImageView getImageViewPoster() {
            return imageViewPoster;
        }
    }
}
