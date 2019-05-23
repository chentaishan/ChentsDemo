package com.example.mvp_upgrade.presenter;

import com.example.mvp_upgrade.view.IView;

public abstract class BasePresenter<V extends IView> {

    protected V iview;


    public BasePresenter(V iview) {
        this.iview = iview;
    }


   void destrory(){
        iview = null;
   }
}
