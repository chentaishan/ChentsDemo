package com.example.mvp_upgrade.view;

public interface UpdateView extends IView {
    void updateSuccess( String result);
    void updateFailed(String error);
}
