package it.unimib.smoovie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieGenre;
import it.unimib.smoovie.utils.Constants;

public class MovieCategoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Notifiable<MovieGenre> {

    private List<MovieGenre> categoryList;
    private final Context context;

    public MovieCategoryViewAdapter(Context context) {
        this.categoryList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_view_category_search_item, parent, false);

        return new MovieCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieCategoryViewAdapter.MovieCategoryViewHolder movieCategoryViewHolder = (MovieCategoryViewAdapter.MovieCategoryViewHolder) holder;
        MovieGenre category = categoryList.get(position);

        Button categoryButton = movieCategoryViewHolder.getButtonCategory();
        Resources resources = context.getResources();

        categoryButton.setText(resources.getIdentifier(String.format("movie_genre_%s", category.getCategory().toLowerCase()), "string", context.getPackageName()));
        categoryButton.setOnClickListener(new ButtonCategoryOnClickListener(category));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void addItems(List<MovieGenre> items) {
        this.categoryList = items;

        this.notifyDataSetChanged();
    }

    public static class MovieCategoryViewHolder extends RecyclerView.ViewHolder {

        private final Button buttonCategory;

        public MovieCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonCategory = itemView.findViewById(R.id.textView_category);
        }

        public Button getButtonCategory() {
            return buttonCategory;
        }
    }

    private final static class ButtonCategoryOnClickListener implements View.OnClickListener {

        private final MovieGenre category;

        public ButtonCategoryOnClickListener(MovieGenre category) {
            this.category = category;
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEARCH_MOVIE_GENRE_BUNDLE_KEY, category.getCategory());

            Navigation.findNavController(view)
                    .navigate(R.id.resultsFragment, bundle);
        }
    }
}
