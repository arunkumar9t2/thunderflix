package arun.com.popularmovies.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import arun.com.popularmovies.R;

/**
 * Created by Arun on 06/12/2015.
 */
public class Utility {
    private static Drawable placeHolder;

    public static Drawable getPlaceholderImage(Context context) {
        if (placeHolder == null) {
            // tint the placeholder image based on app's material accent color
            placeHolder = ContextCompat.getDrawable(context, R.drawable.ic_movie_black_48dp);
            Drawable wrapDrawable = DrawableCompat.wrap(placeHolder);
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, R.color.colorAccent));
        }
        return placeHolder;
    }

    public static int getDarkColorFromPalette(Palette palette, int defaultColor) {
        if (palette != null) {
            if (palette.getDarkVibrantSwatch() != null) {
                return palette.getDarkVibrantSwatch().getRgb();
            } else if (palette.getDarkMutedSwatch() != null) {
                return palette.getDarkMutedSwatch().getRgb();
            } else if (palette.getMutedSwatch() != null) {
                return palette.getMutedSwatch().getRgb();
            }
        }
        return defaultColor;
    }

    public static String getReleaseDate(String date) {
        if (TextUtils.isEmpty(date)) return "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date));
            return calendar.get(Calendar.DATE)
                    + " " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
                    + " " + calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            return date;
        }
    }

}
