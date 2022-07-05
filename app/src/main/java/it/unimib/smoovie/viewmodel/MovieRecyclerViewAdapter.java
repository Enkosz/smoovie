package it.unimib.smoovie.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.data.model.MovieModel;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private final List<MovieModel> movieModelList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewIcon = itemView.findViewById(R.id.imageView_icon);
        }

        public ImageView getImageViewIcon() {
            return imageViewIcon;
        }
    }

    public MovieRecyclerViewAdapter(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        MovieModel model = movieModelList.get(position);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/original/" + model.posterPath)
                .into(holder.getImageViewIcon());
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }
}
