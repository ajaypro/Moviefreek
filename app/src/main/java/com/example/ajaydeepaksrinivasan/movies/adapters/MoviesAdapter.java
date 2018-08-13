package com.example.ajaydeepaksrinivasan.movies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ajaydeepaksrinivasan.movies.Models.Result;
import com.example.ajaydeepaksrinivasan.movies.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    List<Result> moviesList;
    Context context;

    public MoviesAdapter(List moviesList, Context context){

        this.context = context;
        this.moviesList = moviesList;
    }



    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_movies,viewGroup,false);
        return new MoviesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder viewHolder, int position) {

        viewHolder.tvTitle.setText(moviesList.get(position).getTitle());
        viewHolder.tvOverview.setText(moviesList.get(position).getOverview());
        viewHolder.tvReleaseDate.setText(moviesList.get(position).getReleaseDate());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+moviesList.get(position).getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.ivMovie);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MoviesHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvOverview,tvReleaseDate;
        ImageView ivMovie;

        public MoviesHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverView);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            ivMovie =itemView.findViewById(R.id.ivMovie);
        }



    }
}
