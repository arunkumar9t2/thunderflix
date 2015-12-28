package arun.com.popularmovies.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.List;

import arun.com.popularmovies.R;
import arun.com.popularmovies.models.Movie;
import arun.com.popularmovies.models.PosterSize;
import arun.com.popularmovies.util.Utility;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private final List<Movie> mDataset;
    private final Context mContext;
    private ItemClickListener externalListener;

    public GridAdapter(Context mContext, List<Movie> myDataset) {
        mDataset = myDataset;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.externalListener = listener;
    }

    public void add(int position, Movie item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // get the image path
        String posterPath = mDataset.get(position).getPoster_path();
        String formattedPath = new PosterSize(mContext).getFormattedPosterPath(posterPath);

        Glide.with(mContext)
                .load(formattedPath)
                .placeholder(Utility.getPlaceholderImage(mContext))
                .crossFade()
                .listener(GlidePalette.with(formattedPath)
                        .use(GlidePalette.Profile.VIBRANT)
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                int darkColor = Utility.getDarkColorFromPalette(
                                        palette,
                                        ContextCompat.getColor(mContext, R.color.grid_footer_default));
                                holder.movieTitle.setBackgroundColor(darkColor);
                            }
                        }))
                .into(holder.movieImage);

        holder.movieTitle.setText(mDataset.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface ItemClickListener {
        void onMovieClicked(int position, Movie movie, ImageView heroElement);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView movieImage;
        public final TextView movieTitle;

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            movieImage = (ImageView) view.findViewById(R.id.griditem_movie_poster);
            movieTitle = (TextView) view.findViewById(R.id.griditem_movie_title);
        }

        @Override
        public void onClick(View v) {
            if (externalListener != null)
                externalListener.onMovieClicked(getAdapterPosition(),
                        mDataset.get(getAdapterPosition()), movieImage);
        }
    }

}