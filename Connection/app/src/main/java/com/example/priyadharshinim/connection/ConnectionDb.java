package com.example.priyadharshinim.connection;

/**
 * Created by priyadharshinim on 03/06/17.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class ConnectionDb extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public ConnectionDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<String> getMovieNames() {

        List<String> names = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"title","year"};
        String sqlTables = "tamil_movies";
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            names.add(c.getString(c.getColumnIndex("title"))+"("+
                    c.getString(c.getColumnIndex("year"))+")"); //add the item
            c.moveToNext();
        }

        c.close();
        return names;

    }

    public int getMovieId(String movieName) {

        int movieId = 0;
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"id"};
        String sqlTables = "tamil_movies";
        qb.setTables(sqlTables);
        qb.appendWhere("title ="+" \""+movieName+"\" ");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();

        movieId=Integer.parseInt(c.getString(c.getColumnIndex("id"))); //add the item

        c.close();
        return movieId;

    }

    public List<String> getCasts(int id, String column) {

        List<String> names = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {column};
        String sqlTables = "tamil_movies";
        qb.setTables(sqlTables);
        qb.appendWhere("id="+id);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();

        String cast = c.getString(c.getColumnIndex(column));
        names = new ArrayList<String>(Arrays.asList(cast.split(",")));
        names.removeAll(Collections.singleton(null));
        names.removeAll(Collections.singleton(""));
        c.close();
        return names;

    }

    public List<String> getMatchingList(List<String> cast, String column) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"title"};
        String sqlTables = "tamil_movies";
        qb.setTables(sqlTables);
        StringBuilder where = new StringBuilder();
        List<String> names = new ArrayList<String>();

        if(!cast.isEmpty() && cast.size()>0) {

            for (int i = 0; i < cast.size(); i++) {
                where.append(column + " like " + " \"" + "%," + cast.get(i) + ",%" + "\" ");

                if (i != cast.size() - 1) {
                    where.append(" OR ");
                }
            }

            qb.appendWhere(where);
            Cursor c = qb.query(db, sqlSelect, null, null,
                    null, null, null);


            c.moveToFirst();

            while (!c.isAfterLast()) {
                names.add(c.getString(c.getColumnIndex("title"))); //add the item
                c.moveToNext();
            }

            c.close();
        }
        return names;

    }

}