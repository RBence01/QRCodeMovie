package com.example.qrcodemovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<Movie> items;
    private Context context;

    public ListAdapter(List<Movie> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);
        TextView director = view.findViewById(R.id.director);
        TextView duration = view.findViewById(R.id.duration);
        TextView rating = view.findViewById(R.id.rating);
        TextView category = view.findViewById(R.id.category);

        Movie movie = items.get(i);

        director.setText(movie.getDirector());
        int time = movie.getTime();
        if (time > 60) duration.setText(String.format("%d óra %dperc", time / 60, time % 60));
        else duration.setText(String.format("%d perc", time));
        rating.setText(movie.getRating() + "/5");
        category.setText(movie.getCategory());

        return view;
    }
}
