package it.unimib.smoovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.smoovie.R;

public class MovieCategoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<String> categoryList;

    public MovieCategoryViewAdapter(List<String> categoryList) {
        this.categoryList = categoryList;
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
        String category = categoryList.get(position);

        movieCategoryViewHolder.getButtonCategory()
                .setText(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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
}
