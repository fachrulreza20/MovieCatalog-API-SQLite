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

import com.example.mymoviecatalog.viewmodel.TVShowViewModel;
import com.example.mymoviecatalog.adapter.ListTVShowAdapter;
import com.example.mymoviecatalog.myclass.TVShow;
import com.example.mymoviecatalog.activity.MainActivity;
import com.example.mymoviecatalog.R;

import java.util.ArrayList;

public class TVShowFragment extends Fragment {

    private ArrayList<TVShow> tvshows;
    private RecyclerView rvCategory;

    public ListTVShowAdapter listTVShowAdapter;
    private ProgressBar progressBar;
    private TVShowViewModel tvshowViewModel;


    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_tvshow, container, false);

        tvshowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvshowViewModel.getTVShow().observe(this, getTVShowObserver);


        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.appbartitle_tvshowlist);
        }

        rvCategory = rootview.findViewById(R.id.rv_category_tvshow);
        rvCategory.setHasFixedSize(true);
        progressBar = rootview.findViewById(R.id.progressBarTVShow);

        showRecyclerList();

        tvshowViewModel.setTVShow();

        return rootview;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<TVShow>> getTVShowObserver = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvshowItems) {
            if (tvshowItems != null) {
                listTVShowAdapter.setListTVShow(tvshowItems);
                showLoading(false);
            }
        }
    };



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading(true);
    }

    public void showRecyclerList(){
        tvshows = new ArrayList<>();
        rvCategory.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listTVShowAdapter = new ListTVShowAdapter();
        listTVShowAdapter.notifyDataSetChanged();
        listTVShowAdapter.setContext(this.getActivity());
        listTVShowAdapter.setListTVShow(tvshows);
        rvCategory.setAdapter(listTVShowAdapter);
    }



}
