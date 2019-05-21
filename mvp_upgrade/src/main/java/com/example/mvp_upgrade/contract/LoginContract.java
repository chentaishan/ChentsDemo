package com.example.mvp_upgrade.contract;

public interface LoginContract {
    interface Model<T> {

        void getLoginData(LoginContract.CallBack<T> callBack);
    }

    interface View<T> extends BaseView {
        void updateSuccess(T t);
        void updateFailed(String error);
    }

    interface Presenter {


    }
    interface CallBack<T>{
        void updateSuccess(T t);
        void updateFailed(String error);
    }
}
