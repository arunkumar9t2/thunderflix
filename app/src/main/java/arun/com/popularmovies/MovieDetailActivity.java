package arun.com.popularmovies;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import arun.com.popularmovies.fragments.MovieGridFragment;
import arun.com.popularmovies.models.Movie;
import arun.com.popularmovies.models.PosterSize;
import arun.com.popularmovies.util.Utility;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private static final String MOVIE_INFO = "movie_info";
    private Movie mMovie;
    private ImageView mBackdrop;
    private FloatingActionButton mFaB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFaB = (FloatingActionButton) findViewById(R.id.fab);
        mFaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Implementation Pending", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        hideFAB();

        mBackdrop = (ImageView) findViewById(R.id.backdrop);

        mMovie = getIntent().getExtras().getParcelable(MOVIE_INFO);
        populateUI();
    }

    private void populateUI() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mMovie.getTitle());

        // load the backdrop image
        String backdropPath = new PosterSize(this).getFormattedBackdropPath(
                mMovie.getBackdrop_path()
        );

        Glide.with(this)
                .load(backdropPath)
                .crossFade()
                .listener(GlidePalette
                        .with(backdropPath)
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                int fabColor = Utility.getDarkColorFromPalette(
                                        palette,
                                        ContextCompat.getColor(
                                                getApplicationContext(),
                                                R.color.colorPrimaryDark));
                                updateFAB(fabColor);
                            }
                        })
                )
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)))
                .into(mBackdrop);

        ((ImageView) findViewById(R.id.movie_poster_detail))
                .setImageDrawable(MovieGridFragment.mPosterCache.get(1));

        String movieName = mMovie.getOriginal_title() != null ?
                mMovie.getOriginal_title() : mMovie.getTitle();

        ((TextView) findViewById(R.id.movie_title)).setText(movieName);

        ((TextView) findViewById(R.id.movie_summary)).setText(mMovie.getOverview());

        ((TextView) findViewById(R.id.movie_release)).setText(
                Utility.getReleaseDate(mMovie.getRelease_date())
        );

        ((TextView) findViewById(R.id.movie_rating)).setText(mMovie.getVote_average() + " / 10");
    }

    private void updateFAB(int darkVibrant) {
        mFaB.setBackgroundTintList(new ColorStateList(new int[][]
                {new int[]{0}},
                new int[]{darkVibrant}));
        showFAB();
    }


    private void hideFAB() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                mFaB.getLayoutParams();
        params.setAnchorId(View.NO_ID);
        mFaB.setLayoutParams(params);
        mFaB.setVisibility(View.GONE);
    }

    private void showFAB() {
        mFaB.show();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                mFaB.getLayoutParams();
        params.setAnchorId(R.id.app_bar);
        mFaB.setLayoutParams(params);
        mFaB.setVisibility(View.VISIBLE);
        mFaB.hide();
        mFaB.show();
    }
}
