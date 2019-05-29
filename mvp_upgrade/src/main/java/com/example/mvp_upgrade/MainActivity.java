package com.example.mvp_upgrade;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvp_upgrade.presenter.LoginPresenter;

public class MainActivity extends BaseActivity<LoginPresenter> implements LoginContract.View<ResultBean>, View.OnClickListener {


    private Button mClick;
    private TextView mResult;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {

        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private static final String TAG = "MainActivity";
    @Override
    public void updateSuccess(ResultBean o) {

        Log.d(TAG, "updateSuccess: "+o.toString());
        mResult.setText(o.toString());

    }

    @Override
    public void updateFailed(String error) {
        Log.d(TAG, "updateFailed: "+error);

        mResult.setText(error);
    }

    @Override
    public Context getContext() {
        return this;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:

                presenter.login();
                break;
        }
    }
}
