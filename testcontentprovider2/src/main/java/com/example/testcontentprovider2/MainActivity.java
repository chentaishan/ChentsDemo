package com.example.testcontentprovider2;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;
    private TextView mResult;
    private EditText mInput;
    private Button mDataInsert;
    private EditText mDelete;
    private Button mDataDelete;
    private EditText mUpdate;
    private Button mDataUpdate;
    private Button mAllQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {

        mResult = (TextView) findViewById(R.id.result);
        mInput = (EditText) findViewById(R.id.input);
        mDataInsert = (Button) findViewById(R.id.insert_data);
        mDataInsert.setOnClickListener(this);
        mDelete = (EditText) findViewById(R.id.delete);
        mDataDelete = (Button) findViewById(R.id.delete_data);
        mDataDelete.setOnClickListener(this);
        mUpdate = (EditText) findViewById(R.id.update);
        mDataUpdate = (Button) findViewById(R.id.update_data);
        mDataUpdate.setOnClickListener(this);
        mAllQuery = (Button) findViewById(R.id.query_all);
        mAllQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.insert_data:// TODO 19/08/08
                String input = mInput.getText().toString();
                if (input == null) {
                    return;
                }

                String params[] ;
                if (input.contains(",")){
                    params = input.split(",");

                    MyOpenHelper.getInstance(this).insert(params[0],params[1]);
                }



                break;
            case R.id.delete_data:// TODO 19/08/08
                  input = mDelete.getText().toString();
                if (input == null) {
                    return;
                }
                MyOpenHelper.getInstance(this).delete(input);
                break;
            case R.id.update_data:// TODO 19/08/08
                  input = mUpdate.getText().toString();
                if (input == null) {
                    return;
                }
                if (input.contains(",")){
                    params = input.split(",");
                }else{
                    return;
                }

                MyOpenHelper.getInstance(this).update(params[0],params[1]);
                break;
            case R.id.query_all:// TODO 19/08/08
                final String s = MyOpenHelper.getInstance(this).queryAll();

                mResult.setText(s);

                break;
            default:
                break;
        }
    }


}
