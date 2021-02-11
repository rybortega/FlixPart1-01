package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.security.InvalidKeyException;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    public static  final int POPULAR = 1;
    public static  final  int CRASH = 0;

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getVoteAverage() >= 5) {
            return POPULAR;
        }
        else {
            return  CRASH;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
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
            Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(ivPoster);
        }
    }

    public class ViewHolderPopular extends ViewHolder {
        ImageView ivBackdrop;

        public ViewHolderPopular(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.popularView);
        }

        public void bind(Movie movie) {
            Glide.with(context).load(movie.getBackdropPath()).placeholder(R.drawable.ic_launcher_background).into(ivBackdrop);
        }
    }
}




