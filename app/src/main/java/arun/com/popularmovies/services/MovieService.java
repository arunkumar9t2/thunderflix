package arun.com.popularmovies.services;

import arun.com.popularmovies.MainActivity;
import arun.com.popularmovies.models.MovieResults;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Arun on 05/12/2015.
 */
public interface MovieService {
    @GET("discover/movie?api_key=" + MainActivity.API)
    Call<MovieResults> popular(
            @Query("page") Integer page,
            @Query("sort_by") String sort_by
    );
}
