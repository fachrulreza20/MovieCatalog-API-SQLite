package com.example.mymoviecatalog.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.example.mymoviecatalog.viewmodel.MovieViewModel;
import com.example.mymoviecatalog.adapter.ListMovieAdapter;
import com.example.mymoviecatalog.myclass.Movie;
import com.example.mymoviecatalog.activity.MainActivity;
import com.example.mymoviecatalog.R;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private RecyclerView rvCategory;

    ListMovieAdapter listMovieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel mainViewModel;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_movie, container, false);

        mainViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mainViewModel.getMovies().observe(this, getMovieObserver);

        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.appbartitle_movielist);
        }

        rvCategory = rootview.findViewById(R.id.rv_category_movie);
        rvCategory.setHasFixedSize(true);
        progressBar = rootview.findViewById(R.id.progressBar);

        showRecyclerList();

        mainViewModel.setMovie();

        return rootview;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<Movie>> getMovieObserver = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movieItems) {
            if (movieItems != null) {
                listMovieAdapter.setListMovie(movieItems);
                showLoading(false);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading(true);
    }


    private void showRecyclerList(){
        ArrayList<Movie> movies;
        movies = new ArrayList<>();
        rvCategory.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listMovieAdapter = new ListMovieAdapter();
        listMovieAdapter.notifyDataSetChanged();
        listMovieAdapter.setContext(this.getActivity());
        listMovieAdapter.setListMovie(movies); // pakai variabel movies di baris 19 pas deklarasi ArrayList<Movie>
        rvCategory.setAdapter(listMovieAdapter);
    }




}
