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
package arun.com.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import arun.com.popularmovies.models.MovieResults;
import arun.com.popularmovies.restApi.RestClient;
import arun.com.popularmovies.services.MovieService;
import retrofit.Call;
import retrofit.Response;

public class MovieFetcherTask extends AsyncTask<Void, Void, MovieResults> {
    public static final String SORT_TYPE_POPULAR = "popularity.desc";
    public static final String SORT_TYPE_RATED = "vote_average.desc";
    public static final String SORT_TYPE_GROSSING = "revenue.desc";
    private static final String TAG = MovieFetcherTask.class.getSimpleName();

    private Call<MovieResults> movieList;

    private ProgressListener externalListener;


    public MovieFetcherTask(String fetchType) {
        // get the rest service
        MovieService service = RestClient.getInstance().getMovieService();

        // instantiate rest service based on sort type
        if (fetchType != null && !fetchType.equalsIgnoreCase("")) {
            if (SORT_TYPE_POPULAR.equalsIgnoreCase(fetchType)) {
                movieList = service.popular(1, SORT_TYPE_POPULAR);
            } else if (SORT_TYPE_RATED.equalsIgnoreCase(fetchType)) {
                movieList = service.popular(1, SORT_TYPE_RATED);
            } else if (SORT_TYPE_GROSSING.equalsIgnoreCase(fetchType)) {
                movieList = service.popular(1, SORT_TYPE_GROSSING);
            }
        }
    }

    public MovieFetcherTask setProgressListener(ProgressListener listener) {
        externalListener = listener;
        // return this instance to facilitate chaining of methods
        return this;
    }

    @Override
    protected void onPreExecute() {
        if (externalListener != null) {
            externalListener.onFetchingStart();
        }
    }

    @Override
    protected MovieResults doInBackground(Void... params) {
        Response<MovieResults> response;
        if (movieList != null) {
            try {
                response = movieList.execute();
                if (response.isSuccess()) {
                    Log.d(TAG, "Response success");
                    return response.body();
                } else {
                    Log.d(TAG, "Response was not a success");
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(MovieResults movieResults) {
        if (externalListener != null) {
            externalListener.onFetchingComplete(movieResults);
        }
    }

    // External Callback to notify progress of fetch operation
    public interface ProgressListener {
        void onFetchingStart();

        void onFetchingComplete(MovieResults movieResults);
    }


}
