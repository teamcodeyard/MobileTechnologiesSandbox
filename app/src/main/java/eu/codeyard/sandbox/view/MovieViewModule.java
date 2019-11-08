package eu.codeyard.sandbox.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import eu.codeyard.sandbox.R;
import eu.codeyard.sandbox.model.Movie;
import io.trieulh.simplegenericadapter.diff.Diffable;
import io.trieulh.simplegenericadapter.holder.SimpleViewHolder;
import io.trieulh.simplegenericadapter.module.ItemModule;

public class MovieViewModule extends ItemModule<Movie> {

    Context context;

    public MovieViewModule(Context context) {
        this.context = context;
    }

    @Override
    public boolean isModule(Diffable diffable) {
        return diffable instanceof Movie;
    }

    @Override
    public boolean isStickyModule(Movie movie) {
        return false;
    }

    @Override
    public void onBind(Movie movie, SimpleViewHolder holder) {
        ((AppCompatTextView) holder.itemView.findViewById(R.id.titleLabel)).setText(movie.getTitle());
        ((AppCompatTextView) holder.itemView.findViewById(R.id.yearLabel)).setText(movie.getYear());
        ((AppCompatTextView) holder.itemView.findViewById(R.id.typeLabel)).setText(movie.getType());
        Glide
            .with(context)
            .load(movie.getPoster())
            .into(((ImageView) holder.itemView.findViewById(R.id.cover)));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_movie_list_item;
    }

    @Override
    public int getType() {
        return 1;
    }
}
