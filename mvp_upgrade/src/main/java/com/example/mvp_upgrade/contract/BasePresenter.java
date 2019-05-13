package com.example.mvp_upgrade.contract;

public abstract class BasePresenter<V extends BaseView> {

    protected LoginContract.View iView;
    public BasePresenter(LoginContract.View  iView) {
        this.iView = iView;
        
        initModel();
    }

    protected abstract void initModel();


     public void onDestrory(){

        if (iView!=null){
            iView=null;
        }
    }
}
