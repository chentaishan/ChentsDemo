package com.example.testcontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton4.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:


                queryAll();
                // TODO 19/05/29
                break;
            case R.id.button2:
                // TODO 19/05/29
                break;
            case R.id.button3:
                // TODO 19/05/29
                break;
            case R.id.button4:
                // TODO 19/05/29
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
