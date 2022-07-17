package it.unimib.smoovie.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.smoovie.R;

public class MovieCategoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

        Button categoryButton = movieCategoryViewHolder.getButtonCategory();

        categoryButton.setText(category);
        categoryButton.setOnClickListener(new ButtonCategoryOnClickListener());
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

    private final static class ButtonCategoryOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("link","http://yourlink.com/policy");

            Navigation.findNavController(view)
                    .navigate(R.id.resultsFragment, bundle);
        }
    }
}
