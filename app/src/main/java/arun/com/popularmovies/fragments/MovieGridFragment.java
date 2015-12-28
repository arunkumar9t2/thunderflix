/*
 * Copyright 2015.  Arunkumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package arun.com.popularmovies.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import arun.com.popularmovies.MovieDetailActivity;
import arun.com.popularmovies.R;
import arun.com.popularmovies.adapters.GridAdapter;
import arun.com.popularmovies.models.Movie;
import arun.com.popularmovies.models.MovieResults;
import arun.com.popularmovies.tasks.MovieFetcherTask;

public class MovieGridFragment extends Fragment {
    public static final SparseArray<Drawable> mPosterCache = new SparseArray<>(1);
    private static final String ARG_SORT_TYPE = "sort_type";
    private static final String TAG = MovieGridFragment.class.getSimpleName();
    private GridAdapter mAdapter;
    private RecyclerView mMoviesGrid;

    public MovieGridFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given sort
     * type.
     */
    public static MovieGridFragment newInstance(String sortType) {
        MovieGridFragment fragment = new MovieGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SORT_TYPE, sortType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);
        mMoviesGrid = (RecyclerView) rootView.findViewById(R.id.movies_grid_recyclerview);
        mMoviesGrid.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        MovieFetcherTask fetcherTask = new MovieFetcherTask(
                getArguments().getString(ARG_SORT_TYPE, MovieFetcherTask.SORT_TYPE_POPULAR));

        fetcherTask.setProgressListener(new MovieFetcherTask.ProgressListener() {
            @Override
            public void onFetchingStart() {
            }

            @Override
            public void onFetchingComplete(MovieResults movieResults) {
                if (movieResults != null && getActivity() != null) {
                    Log.d(TAG, "Fetching complete, rendering grid");
                    mAdapter = new GridAdapter(getActivity().getApplicationContext(),
                            movieResults.getResults());

                    mAdapter.setOnItemClickListener(new GridAdapter.ItemClickListener() {
                        @Override
                        public void onMovieClicked(int position, Movie movie,
                                                   ImageView movieImage) {
                            // create the detail activity intent
                            Intent movieDetailActivity = new Intent(getActivity(),
                                    MovieDetailActivity.class);
                            // add movie information
                            movieDetailActivity.putExtra("movie_info", movie);

                            // store the poster image for transition
                            mPosterCache.clear();
                            mPosterCache.put(1, movieImage.getDrawable());

                            getActivity().startActivity(movieDetailActivity,
                                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            getActivity(),
                                            movieImage,
                                            "robot").toBundle());
                        }
                    });
                    mMoviesGrid.setAdapter(mAdapter);
                } else {
                    Toast.makeText(getActivity(),
                            getActivity().getString(R.string.error),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
        return rootView;
    }
}
