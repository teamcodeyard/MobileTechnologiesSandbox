package eu.codeyard.sandbox;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.adroitandroid.chipcloud.ChipCloud;
import com.bumptech.glide.Glide;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

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

@EActivity(R.layout.activity_movie_detail_styled)
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
    AppCompatTextView plotLabel;

    @ViewById
    ImageView cover;

    @Extra
    Movie movie;

    @ViewsById({R.id.rating1, R.id.rating2, R.id.rating3})
    List<ColorfulRingProgressView> ratingRings;

    @ViewsById({R.id.ratingLabel1, R.id.ratingLabel2, R.id.ratingLabel3})
    List<AppCompatTextView> ratingLabels;

    @ViewsById({R.id.percentText1, R.id.percentText2, R.id.percentText3})
    List<AppCompatTextView> percentTextLabels;

    @ViewsById({R.id.ratingContainer1, R.id.ratingContainer2, R.id.ratingContainer3})
    List<ConstraintLayout> ratingContainers;

    @ViewById
    ChipCloud actorList;

    @AfterViews
    public void init() {
        bindBasicInfo();
        fetchMovieDetails();
    }

    @UiThread
    void bindBasicInfo() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(movie.getTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        yearLabel.setText(getResources().getString(R.string.movie_info_year, movie.getYear()));
        showCoverImage();
        for (View view : ratingContainers) {
            view.setVisibility(View.GONE);
        }
    }

    @UiThread
    void bindDetailInfo(MovieDetail movieDetail) {
        genreLabel.setText(movieDetail.getGenre());
        runtimeLabel.setText(movieDetail.getRuntime());
        directorLabel.setText(movieDetail.getDirector());
        actorList.addChips(movieDetail.getActorList());
        plotLabel.setText(movieDetail.getPlot());

        for (int i = 0; i < Math.min(movieDetail.getRatings().size(), 3); ++i) {
            MovieRating rating = movieDetail.getRatings().get(i);
            if (rating != null) {
                setupRatingColor(ratingRings.get(i), rating);
                ratingLabels.get(i).setText(rating.getSource());
                percentTextLabels.get(i).setText(getResources().getString(R.string.percent, (int) rating.getPercent()));
                ratingContainers.get(i).setVisibility(View.VISIBLE);
                ratingContainers.get(i).setAlpha(0f);
                ratingContainers.get(i).animate().alpha(1f).setDuration(500).start();
            }

        }
    }

    @UiThread
    void setupRatingColor(ColorfulRingProgressView view, MovieRating rating) {
        view.setPercent((float) rating.getPercent());
        view.setFgColorStart(ContextCompat.getColor(MovieDetailActivity.this, provideColorForRating(rating.getPercent())));
        view.setFgColorEnd(ContextCompat.getColor(MovieDetailActivity.this, provideColorForRating(rating.getPercent())));
    }

    private int provideColorForRating(double percent) {
        if (percent < 30) {
            return R.color.colorRatingBad;
        } else if (percent >= 30 && percent <= 60) {
            return R.color.colorRatingGood;
        } else {
            return R.color.colorRatingExcellent;
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
