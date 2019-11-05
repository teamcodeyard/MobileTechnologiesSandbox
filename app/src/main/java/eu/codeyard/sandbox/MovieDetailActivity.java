package eu.codeyard.sandbox;

import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.sandbox.model.Movie;

@EActivity(R.layout.activity_movie_detail)
public class MovieDetailActivity extends AppCompatActivity {

    @ViewById
    AppCompatTextView title;

    @ViewById
    ImageView cover;

    @Extra
    Movie movie;

    @AfterViews
    public void init() {
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(movie.getYear());
        showCoverImage();
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
