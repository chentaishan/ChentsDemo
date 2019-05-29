package com.example.mvp_upgrade;

public class DailyFragment<UpdatePresenter> extends BaseFragment implements UpdateContract.View {
    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter initPresenter() {

        return updatePresenter;
    }

    @Override
    public int getlayouId() {
        return 0;
    }

    @Override
    public int initData() {
        return 0;
    }
}
