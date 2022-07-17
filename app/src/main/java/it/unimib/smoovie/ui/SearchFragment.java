package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import it.unimib.smoovie.R;
import it.unimib.smoovie.adapter.MovieCategoryViewAdapter;

public class SearchFragment extends Fragment {

    private final List<String> categoryList = new ArrayList<>();

    public SearchFragment() {
        super();

        categoryList.add("A");
        categoryList.add("B");
        categoryList.add("C");
        categoryList.add("D");
        categoryList.add("E");
        categoryList.add("F");
        categoryList.add("G");
        categoryList.add("H");
        categoryList.add("I");
        categoryList.add("P");
        categoryList.add("Q");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView mRecyclerViewCountryNews = view.findViewById(R.id.recyclerView_search);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext(), FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        MovieCategoryViewAdapter adapter = new MovieCategoryViewAdapter(categoryList);
        mRecyclerViewCountryNews.setLayoutManager(layoutManager);
        mRecyclerViewCountryNews.setAdapter(adapter);

        return view;
    }
}
