package eu.codeyard.sandbox;

import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import eu.codeyard.sandbox.model.Movie;
import eu.codeyard.sandbox.model.MovieDetail;
import eu.codeyard.sandbox.model.MovieRating;
import eu.codeyard.sandbox.network.MovieApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_movie_detail)
public class MovieDetailActivity extends AppCompatActivity {

    @Bean
    public MovieApi movieApi;

    @ViewById
    AppCompatTextView yearLabel;

    @ViewById
    AppCompatTextView genreLabel;

    @ViewById
    AppCompatTextView runtimeLabel;

    @ViewById
    AppCompatTextView directorLabel;

    @ViewById
    AppCompatTextView actorsLabel;

    @ViewById
    AppCompatTextView plotLabel;

    @ViewById
    ImageView cover;

    @ViewsById({R.id.ratingTitle1, R.id.ratingTitle2, R.id.ratingTitle3})
    List<AppCompatTextView> ratingTitleLabels;

    @ViewsById({R.id.ratingValue1, R.id.ratingValue2, R.id.ratingValue3})
    List<AppCompatTextView> ratingValueLabels;

    @Extra
    Movie movie;

    @AfterViews
    public void init() {
        fetchMovieDetails();
        bindBasicInfo();
    }

    @UiThread
    void bindBasicInfo() {
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        yearLabel.setText(getResources().getString(R.string.movie_info_year, movie.getYear()));
        showCoverImage();
    }

    @UiThread
    void bindDetailInfo(MovieDetail movieDetail) {
        genreLabel.setText(getResources().getString(R.string.movie_info_genre, movieDetail.getGenre()));
        runtimeLabel.setText(getResources().getString(R.string.movie_info_runtime, movieDetail.getRuntime()));
        directorLabel.setText(getResources().getString(R.string.movie_info_director, movieDetail.getDirector()));
        actorsLabel.setText(getResources().getString(R.string.movie_info_actors, movieDetail.getActors()));
        plotLabel.setText(movieDetail.getPlot());

        for (int i = 0; i < Math.max(movieDetail.getRatings().size(), 3); ++i) {
            MovieRating rating = movieDetail.getRatings().get(i);
            if (rating != null) {
                ratingTitleLabels.get(i).setText(rating.getSource());
                ratingValueLabels.get(i).setText(rating.getValue());
            }

        }
    }

    @Background
    void fetchMovieDetails() {
        movieApi.getMovie(movie.getImdbID(), new Callback<MovieDetail>() {
            @Override
            public void success(MovieDetail movieDetail, Response response) {
                bindDetailInfo(movieDetail);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void showCoverImage() {
        Glide
            .with(this)
            .load(movie.getPoster())
            .into(cover);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
