package it.unimib.smoovie.adapter;

import java.util.List;

public interface Notifiable<T> {

    void addItems(List<T> items);
}
