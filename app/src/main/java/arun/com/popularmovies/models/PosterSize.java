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
package arun.com.popularmovies.models;

import android.content.Context;

public class PosterSize {
    private final Context mContext;
    private final String URL_BASE = "http://image.tmdb.org/t/p/";
    private final String SMALL = "w185";
    private final String MEDIUM = "w342";
    private final String LARGE = "w500";
    private final String XLARGE = "w780";

    public PosterSize(Context context) {
        mContext = context;
    }

    private String getPosterSize() {
        float density = mContext.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return XLARGE;
        }
        if (density >= 3.0) {
            return LARGE;
        }
        if (density >= 2.0) {
            return MEDIUM;
        }
        if (density >= 1.5) {
            return MEDIUM;
        }
        if (density >= 1.0) {
            return SMALL;
        }
        return SMALL;
    }

    private String getBackdropSize() {
        float density = mContext.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return XLARGE;
        }
        if (density >= 3.0) {
            return XLARGE;
        }
        if (density >= 2.0) {
            return LARGE;
        }
        if (density >= 1.5) {
            return LARGE;
        }
        if (density >= 1.0) {
            return MEDIUM;
        }
        return MEDIUM;
    }

    public String getFormattedBackdropPath(String orgPath) {
        return URL_BASE + getBackdropSize() + orgPath;
    }

    public String getFormattedPosterPath(String orgPath) {
        return URL_BASE + getPosterSize() + orgPath;
    }
}