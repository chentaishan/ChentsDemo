package com.example.testcontentprovider2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static MyOpenHelper myOpenHelper;

    public static MyOpenHelper getInstance(Context context) {
        if (myOpenHelper == null) {
            myOpenHelper = new MyOpenHelper(context);
        }
        return myOpenHelper;
    }

    private MyOpenHelper(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table user(id INTEGER PRIMARY KEY ," +
                "name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void insert(String id,String name) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", Integer.valueOf(id));
        contentValues.put("name", name);

        myOpenHelper.getWritableDatabase().insert("user", "", contentValues);


    }

    public void delete(String id) {
        myOpenHelper.getWritableDatabase().delete("user", "id=?", new String[]{id + ""});
    }

    public void update(String oldName, String newName) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", newName);

        myOpenHelper.getWritableDatabase().update("user", contentValues, "name=?", new String[]{oldName});
    }

    public String queryAll() {

        final SQLiteDatabase database = myOpenHelper.getWritableDatabase();

        final Cursor cursor = database.query("user", new String[]{}, "", new String[]{}, "", "", "");

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {

            final String id = cursor.getInt(cursor.getColumnIndex("id"))+"";
            final String name = cursor.getString(cursor.getColumnIndex("name"));

            stringBuffer.append(id + "--" + name + "--");

        }

        return stringBuffer.toString();

    }
}
