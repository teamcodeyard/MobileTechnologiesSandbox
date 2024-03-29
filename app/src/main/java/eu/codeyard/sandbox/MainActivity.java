package eu.codeyard.sandbox;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iammert.library.ui.multisearchviewlib.MultiSearchView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.codeyard.sandbox.event.MovieOpened;
import eu.codeyard.sandbox.model.Movie;
import eu.codeyard.sandbox.model.MovieSearchResult;
import eu.codeyard.sandbox.network.MovieApi;
import eu.codeyard.sandbox.view.MovieEmptyModule;
import eu.codeyard.sandbox.view.MovieViewModule;
import io.trieulh.simplegenericadapter.SimpleGenericAdapter;
import io.trieulh.simplegenericadapter.diff.Diffable;
import io.trieulh.simplegenericadapter.holder.SimpleViewHolder;
import io.trieulh.simplegenericadapter.listener.OnItemSelectedListener;
import io.trieulh.simplegenericadapter.module.PagingModule;
import io.trieulh.simplegenericadapter.utils.animation.SimpleAnimationType;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    public MovieApi movieApi;

    protected SimpleGenericAdapter adapter;

    @ViewById
    RecyclerView listView;

    @ViewById
    MultiSearchView multiSearchView;

    private String lastSearchTerm = null;
    private int lastSearchPage = 1;

    @AfterViews
    void init() {
        initSearchBox();
        initRecyclerView();
    }

    @UiThread
    public void initRecyclerView() {
        listView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SimpleGenericAdapter.Builder()
                .addItemAnimation(SimpleAnimationType.SLIDE_IN_BOTTOM)
                .addItemModule(new MovieViewModule(this).addOnItemSelectedListener(new OnItemSelectedListener<Movie>() {
                    @Override
                    public void onItemSelected(int i, Movie movie) {
                        EventBus.getDefault().post(new MovieOpened(movie));
                    }
                }))
                .addEmptyModule(new MovieEmptyModule())
                .addPagingModule(new PagingModule() {
                    @Override
                    public int withVisibleThreshold() {
                        return 3;
                    }

                    @Override
                    public void onBind(SimpleViewHolder simpleViewHolder) {

                    }

                    @Override
                    public void onLoadMore(int i) {
                        loadNextPage(++lastSearchPage);
                    }

                    @Override
                    public int getLayoutRes() {
                        return R.layout.view_movie_list_loading;
                    }
                })
                .attachTo(listView)
                .build();
        setEmptyList();
        search("Star wars", 1);
    }

    @UiThread
    void setEmptyList() {
        adapter.setItems(new ArrayList<Movie>());
    }


    @UiThread
    public void initSearchBox() {
        multiSearchView.setSearchViewListener(new MultiSearchView.MultiSearchViewListener() {
            @Override
            public void onTextChanged(int i, CharSequence charSequence) {

            }

            @Override
            public void onSearchComplete(int i, CharSequence charSequence) {
                search(charSequence.toString(), 1);
            }

            @Override
            public void onSearchItemRemoved(int i) {

            }

            @Override
            public void onItemSelected(int i, CharSequence charSequence) {
                search(charSequence.toString(), 1);
            }
        });
    }

    @UiThread
    void updateList(MovieSearchResult searchResult, int page) {
        List<Diffable> newList = new ArrayList<>();
        if (page > 1) {
            newList.addAll(adapter.getItems());
        }
        newList.addAll(searchResult.getSearchResult());
        adapter.setItems(newList);
    }

    @Background
    void loadNextPage(int i) {
        search(lastSearchTerm, i);
    }


    @Background
    public void search(String term, final int page) {
        this.lastSearchTerm = term;
        this.lastSearchPage = page;
        movieApi.search(term, page, new Callback<MovieSearchResult>() {
            @Override
            public void success(MovieSearchResult searchResult, Response response) {
                Log.d("MOVIES", searchResult.toString());
                if (searchResult.getSearchResult().size() > 0) {
                    updateList(searchResult, page);
                } else if (page == 1) {
                    setEmptyList();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (page == 1) {
                    setEmptyList();
                }
            }
        });
    }

    @Subscribe
    public void onMovieSelected(MovieOpened event) {
        MovieDetailActivity_.intent(this).movie(event.getMovie()).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
