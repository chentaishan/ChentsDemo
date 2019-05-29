package com.example.mvp_upgrade.view;

public interface LoginView extends IView {

    void loginSuccess( String result);
    void loginFailed(String error);
}
