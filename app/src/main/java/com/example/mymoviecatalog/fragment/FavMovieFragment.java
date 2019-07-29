package com.example.mymoviecatalog.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mymoviecatalog.R;
import com.example.mymoviecatalog.activity.MainActivity;
import com.example.mymoviecatalog.adapter.ListMovieAdapter;
import com.example.mymoviecatalog.callback.MyCallBack;
import com.example.mymoviecatalog.database.MovieHelper;
import com.example.mymoviecatalog.myclass.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;



public class FavMovieFragment extends Fragment implements View.OnClickListener, MyCallBack {

    private RecyclerView rvFavoriteMovie;
    private ProgressBar progressBar;
    ListMovieAdapter listMovieAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieHelper movieHelper;
    View rootview;

    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_fav_movie, container, false);


        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, listMovieAdapter.getListMovies());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Favorite");
        }


        rvFavoriteMovie = rootview.findViewById(R.id.rv_category_movie_fav);
        progressBar = rootview.findViewById(R.id.progressBarFavm);

        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvFavoriteMovie.setHasFixedSize(true);

        movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
        movieHelper.open();

        listMovieAdapter = new ListMovieAdapter();
        listMovieAdapter.setContext(this.getActivity());

        if (savedInstanceState == null) {
            new LoadMoviesAsync(movieHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                listMovieAdapter.notifyDataSetChanged();
                listMovieAdapter.setListMovie(list);

            }
        }

        rvFavoriteMovie.setAdapter(listMovieAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        listMovieAdapter.setListMovie(movies);
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> weakNoteHelper;
        private final WeakReference<MyCallBack> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, MyCallBack callback) {
            weakNoteHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllMovies();
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

}

