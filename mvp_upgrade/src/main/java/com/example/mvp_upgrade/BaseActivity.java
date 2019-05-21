package com.example.mvp_upgrade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mvp_upgrade.contract.BasePresenter;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

   protected P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        initView();
        presenter=initPresenter();
    }

    protected abstract P initPresenter();

    protected abstract void initView();

    protected abstract int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestrory();
    }
}
