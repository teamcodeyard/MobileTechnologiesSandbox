package eu.codeyard.sandbox.view;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import eu.codeyard.sandbox.R;
import eu.codeyard.sandbox.model.Movie;
import io.trieulh.simplegenericadapter.diff.Diffable;
import io.trieulh.simplegenericadapter.holder.SimpleViewHolder;
import io.trieulh.simplegenericadapter.module.ItemModule;

public class MovieViewModule extends ItemModule<Movie> {

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
        ((AppCompatTextView) holder.itemView.findViewById(R.id.title)).setText(movie.getTitle());
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
