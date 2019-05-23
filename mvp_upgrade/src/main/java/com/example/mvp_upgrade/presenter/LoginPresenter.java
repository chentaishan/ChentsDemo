package com.example.mvp_upgrade.presenter;

import com.example.mvp_upgrade.ResultBean;
import com.example.mvp_upgrade.model.LoginModel;
import com.example.mvp_upgrade.view.IView;

public class LoginPresenter extends BasePresenter {

    private LoginModel loginModel;

    public LoginPresenter(IView iView) {
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
