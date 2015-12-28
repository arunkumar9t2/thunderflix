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
package arun.com.popularmovies.restApi;

import arun.com.popularmovies.services.MovieService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

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
