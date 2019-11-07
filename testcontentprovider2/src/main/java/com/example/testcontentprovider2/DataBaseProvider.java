package com.example.testcontentprovider2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataBaseProvider extends ContentProvider {

    private static final int insert_item = 1;
    private static final int delete_item = 2;
    private static final int update_item = 3;
    private static final int query_item = 4;
    public static final UriMatcher uriMatcher;

    private static final String authority = "com.example.testcontentprovider2.provider";
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority, "user/insert", insert_item);
        uriMatcher.addURI(authority, "user/delete", delete_item);
        uriMatcher.addURI(authority, "user/update", update_item);
        uriMatcher.addURI(authority, "user/query", query_item);
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
                cursor = MyOpenHelper.getInstance(getContext()).getWritableDatabase().query("user", projection, selection, selectionArgs, null, null, sortOrder);
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
        if (uriMatcher.match(uri) == insert_item) {
            final Integer id = values.getAsInteger("id");
            MyOpenHelper.getInstance(getContext()).insert(id+"",values.getAsString("name"));
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == delete_item) {

            MyOpenHelper.getInstance(getContext()).getWritableDatabase().delete("user","id=?" ,selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        MyOpenHelper.getInstance(getContext()).getWritableDatabase().update("user",values,"id=?",selectionArgs);
        return 0;
    }
}
