package com.example.mymoviecatalog.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.mymoviecatalog.myclass.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "0aefcc962379dd0b5f74d0a2b00bc90d";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();


    public void setMovie() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String bahasa = "en-US";
        String systemLanguage = Locale.getDefault().getDisplayLanguage();

        if(systemLanguage.equals("English")){
            bahasa = "en-US";
        }
        else if(systemLanguage.equals("Indonesia")){
            bahasa = "id-ID";
        }

        // https://api.themoviedb.org/3/discover/movie?api_key=0aefcc962379dd0b5f74d0a2b00bc90d&language=en-US
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language="+bahasa;


        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });



    }
    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }



}