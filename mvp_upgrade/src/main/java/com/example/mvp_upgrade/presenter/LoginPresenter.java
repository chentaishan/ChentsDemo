package com.example.mvp_upgrade.presenter;

import com.example.mvp_upgrade.ResultBean;
import com.example.mvp_upgrade.contract.BasePresenter;
import com.example.mvp_upgrade.contract.LoginContract;
import com.example.mvp_upgrade.model.LoginModel;

public class LoginPresenter extends BasePresenter {

    private LoginModel loginModel;

    public LoginPresenter(LoginContract.View iView) {
        super(iView);
    }

    @Override
    protected void initModel() {

        loginModel = new LoginModel();
    }

    public void login(){

        loginModel.getLoginData(new LoginContract.CallBack<ResultBean>() {

            @Override
            public void updateSuccess(ResultBean resultBean) {

                iView.updateSuccess(resultBean);

            }

            @Override
            public void updateFailed(String error) {

                iView.updateFailed(error);
            }
        });
    }
}
