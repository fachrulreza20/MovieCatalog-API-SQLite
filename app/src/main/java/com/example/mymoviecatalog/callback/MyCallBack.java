package com.example.mymoviecatalog.callback;

import com.example.mymoviecatalog.myclass.Movie;

import java.util.ArrayList;

public interface MyCallBack {

    void preExecute();
    void postExecute(ArrayList<Movie> movies);

}
