package it.unimib.smoovie.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNotifiableListRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Notifiable<T> {

    protected List<T> items;

    public AbstractNotifiableListRecyclerViewAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public void addItems(List<T> items) {
        int currentSize = this.getItemCount();
        this.items.addAll(items);
        this.notifyItemRangeChanged(currentSize, this.items.size() - 1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
