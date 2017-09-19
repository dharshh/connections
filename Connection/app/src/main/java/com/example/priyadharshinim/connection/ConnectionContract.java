package com.example.priyadharshinim.connection;

/**
 * Created by priyadharshinim on 03/06/17.
 */

import android.provider.BaseColumns;

public class ConnectionContract {

    public static final class movieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie_list";
        public static final String COLUMN_MOVIE_NAME = "movie_name";
        public static final String COLUMN_DIRECTED_BY = "directed_by";
        public static final String COLUMN_CAST = "cast";
        public static final String COLUMN_MUSIC = "music";
        public static final String COLUMN_PRODUCTION = "production";
    }

}