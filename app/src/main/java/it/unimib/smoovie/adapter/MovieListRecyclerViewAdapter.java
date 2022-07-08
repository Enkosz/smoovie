package it.unimib.smoovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.logging.Logger;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieModel;

public class MovieListRecyclerViewAdapter extends RecyclerView.Adapter<MovieListRecyclerViewAdapter.ViewHolder> {

    private final Logger logger = Logger.getLogger(MovieListRecyclerViewAdapter.class.getName());
    private final List<MovieModel> movieModelList;
    private Context context;

    public MovieListRecyclerViewAdapter(List<MovieModel> movieModelList, Context context) {
        this.movieModelList = movieModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_movie_item, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        MovieModel model = movieModelList.get(position);

        logger.info("onBindViewHolder for movie in position: " + position + " and with icon path: https://image.tmdb.org/t/p/original/" + model.posterPath);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/original/" + model.posterPath)
                .into(holder.getImageViewMovieIcon());
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewMovieIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewMovieIcon = itemView.findViewById(R.id.imageView_movie_icon);
        }

        public ImageView getImageViewMovieIcon() {
            return imageViewMovieIcon;
        }
    }
}
