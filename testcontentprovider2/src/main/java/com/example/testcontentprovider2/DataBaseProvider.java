package com.example.testcontentprovider2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataBaseProvider extends ContentProvider {

    private static final int query_item = 0;

    public static final UriMatcher uriMatcher;


    private static final String authority = "com.example.testcontentprovider2.provider";

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(authority, "user", query_item);

    }

    @Override
    public boolean onCreate() {


        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {

            case query_item:

                final SQLiteDatabase writableDatabase = MyApp.getMyOpenHelper().getWritableDatabase();

                cursor = writableDatabase.query("user", projection, selection, selectionArgs, null, null, sortOrder);

                break;

        }


        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
