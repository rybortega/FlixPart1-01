package com.example.flixter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.security.InvalidKeyException;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.BindViewHolder>{
    public static  final int POPULAR = 1;
    public static  final  int CRASH = 0;

    Context context;
    List<Movie> movies;
    Activity activity;

    public MovieAdapter(Context context, List<Movie> movies, Activity activity) {
        this.context = context;
        this.movies = movies;
        this.activity = activity;
    }


    @NonNull
    @Override
    public BindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            View movieView1 = LayoutInflater.from(context).inflate(R.layout.item_popular, parent, false);
            return  new ViewHolderPopular(movieView1);
        }
        else {
            View movieView2 = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView2);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BindViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getVoteAverage() >= MainActivity.POPULAR_THRESHOLD) {
            return POPULAR;
        }
        else {
            return  CRASH;
        }
    }

    public class ViewHolder extends BindViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.rvMovies);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            if(this.getLayoutPosition() % 2 == 1)
            {
                itemView.findViewById(R.id.rvMovies).setBackgroundColor(Color.parseColor("#e8e8e8"));
                tvOverview.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_view_border2));
            }
            else
            {
                itemView.findViewById(R.id.rvMovies).setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvOverview.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_view_border1));
            }
            String imageUrl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }
            else {
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context)
                    .load(imageUrl)
                    .transform(new RoundedCorners(30))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(activity,
                                    Pair.create((View)tvTitle, "title"),
                                    Pair.create((View)tvOverview, "overview"));
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }

    public class ViewHolderPopular extends BindViewHolder {
        ImageView ivBackdrop;
        //ImageView ivIcon;
        RelativeLayout container;

        public ViewHolderPopular(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.popularView);
            container = itemView.findViewById(R.id.rvMovies);
            //ivIcon = itemView.findViewById(R.id.playIcon);
        }

        public void bind(Movie movie) {
            Glide.with(context)
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivBackdrop);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }

    abstract class BindViewHolder extends RecyclerView.ViewHolder
    {
        public BindViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(Movie movie);
    }
}




