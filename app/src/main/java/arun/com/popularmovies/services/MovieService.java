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
package arun.com.popularmovies.services;

import arun.com.popularmovies.MainActivity;
import arun.com.popularmovies.models.MovieResults;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieService {
    @GET("discover/movie?api_key=" + MainActivity.API)
    Call<MovieResults> popular(
            @Query("page") Integer page,
            @Query("sort_by") String sort_by
    );
}
