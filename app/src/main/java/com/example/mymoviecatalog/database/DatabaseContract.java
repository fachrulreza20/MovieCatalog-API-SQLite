package com.example.mymoviecatalog.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVORITE = "tb_favorite";
    static final class FavoritColumns{
        static String IDFAV = "idfav";
        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String RELEASE_DATE = "releasedate";
        static String PHOTO_PATH = "photopath";
    }
}