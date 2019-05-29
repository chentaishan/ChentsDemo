package com.example.testcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class MyProvider extends ContentProvider {

    private static final String authorities = "com.example.testcontentprovider.provider";

    public static final int queryAll = 0;
    public static UriMatcher uriMatcher;

    private static final String TAG = "MyProvider";

    static {


        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(authorities, "user", queryAll);

    }

    @Override
    public boolean onCreate() {
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)){

            case queryAll:

                Log.d(TAG, "query: ");
                break;
        }
        return null;
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
