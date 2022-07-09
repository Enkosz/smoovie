package it.unimib.smoovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModel;

public class MovieListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NEWS_VIEW_TYPE = 0;
    private static final int LOADING_VIEW_TYPE = 1;

    private final Logger logger = Logger.getLogger(MovieListRecyclerViewAdapter.class.getName());
    private final List<MovieModel> movieModelList;
    private Context context;

    public MovieListRecyclerViewAdapter(List<MovieModel> movieModelList, Context context) {
        this.movieModelList = movieModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        context = parent.getContext();

        if (viewType == NEWS_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_view_movie_item, parent, false);
            return new MovieViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.news_loading_item, parent, false);
            return new LoadingMovieViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            MovieModel model = movieModelList.get(position);

            logger.info("onBindViewHolder for movie in position: " + position + " and with icon path: https://image.tmdb.org/t/p/original/" + model.posterPath);
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original/" + model.posterPath)
                    .into(movieViewHolder.getImageViewMovieIcon());
        } else if (holder instanceof LoadingMovieViewHolder) {
            ((LoadingMovieViewHolder) holder).activate();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return movieModelList.get(position) == null ? LOADING_VIEW_TYPE : NEWS_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
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

    public static class LoadingMovieViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressBar;

        LoadingMovieViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressbar_loading_news);
        }

        public void activate() {
            progressBar.setIndeterminate(true);
        }
    }
}
