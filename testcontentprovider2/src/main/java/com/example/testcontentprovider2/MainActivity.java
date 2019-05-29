package com.example.testcontentprovider2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        final MyOpenHelper myOpenHelper = MyApp.getMyOpenHelper();

        final SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
        for (int i = 0; i < 10; i++) {


            ContentValues contentValues = new ContentValues();

            contentValues.put("id", i + "");
            contentValues.put("name", i + "gggg");


            writableDatabase.insert("user", "", contentValues);
        }

    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 19/05/29

                queryAll();
                break;
            default:
                break;
        }
    }

    private void queryAll() {

        final Uri parse = Uri.parse("content://com.example.testcontentprovider2.provider/user");


        final Cursor cursor = getContentResolver().query(parse, null, null, null, null);

        StringBuffer stringBuffer = new StringBuffer();

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name= cursor.getString(cursor.getColumnIndex("name"));
            stringBuffer.append(id+"--"+name+"/n");

        }

        cursor.close();



        mResult.setText(stringBuffer.toString());


    }
}
