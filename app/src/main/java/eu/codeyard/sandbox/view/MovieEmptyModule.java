package eu.codeyard.sandbox.view;

import eu.codeyard.sandbox.R;
import io.trieulh.simplegenericadapter.holder.SimpleViewHolder;
import io.trieulh.simplegenericadapter.module.EmptyModule;
import io.trieulh.simplegenericadapter.module.ItemModule;

public class MovieEmptyModule extends EmptyModule {

    @Override
    public void onBind(SimpleViewHolder simpleViewHolder) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_movie_list_empty;
    }
}
