package arun.com.popularmovies.restApi;

import arun.com.popularmovies.services.MovieService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Arun on 05/12/2015.
 */
public class RestClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static RestClient ourInstance;
    private Retrofit retrofit;

    private RestClient() {
    }

    public static RestClient getInstance() {
        if (ourInstance == null)
            return new RestClient();
        else
            return ourInstance;
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public MovieService getMovieService() {
        return getRetrofit().create(MovieService.class);
    }

}
