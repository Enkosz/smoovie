package it.unimib.smoovie.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.model.MovieCategory;
import it.unimib.smoovie.utils.Constants;

public class MovieCategoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Notifiable<MovieCategory> {

    private List<MovieCategory> categoryList;

    public MovieCategoryViewAdapter() {
        this.categoryList = new ArrayList<>();
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
        MovieCategory category = categoryList.get(position);

        Button categoryButton = movieCategoryViewHolder.getButtonCategory();

        categoryButton.setText(category.getCategory());
        categoryButton.setOnClickListener(new ButtonCategoryOnClickListener(category));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void addItems(List<MovieCategory> items) {
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

        private final MovieCategory category;

        public ButtonCategoryOnClickListener(MovieCategory category) {
            this.category = category;
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEARCH_MOVIE_CATEGORY_BUNDLE_KEY, category.getCategory());

            Navigation.findNavController(view)
                    .navigate(R.id.resultsFragment, bundle);
        }
    }
}
