package com.example.mvp_upgrade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 抽取  view  P 懒加载
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {


    P presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(getlayouId(),null);

        initView();
        initData();
        presenter=initPresenter();
        return root;
    }

    protected abstract void initView();

    protected  abstract P initPresenter();

    public abstract int getlayouId();

    public abstract int initData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


    }


}
