package com.example.mymoviecatalog.callback;

import com.example.mymoviecatalog.myclass.TVShow;

import java.util.ArrayList;

public interface TvsCallBack {

    void preExecute();
    void postExecute(ArrayList<TVShow> tvshows);

}
